import React, { useState, useEffect } from 'react'
import { Card, Row, Col, InputNumber, Button, Spin, Alert, Empty, Badge, Space, Typography } from 'antd'
import { ArrowLeftOutlined, ShoppingCartOutlined } from '@ant-design/icons'
import { getMenuItems } from '../api'
import OrderForm from './OrderForm'

const { Title, Text } = Typography
const { Meta } = Card

function MenuView({ restaurant, selectedItems, onItemsChange, onPlaceOrder, onBack }) {
  const [menuItems, setMenuItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showOrderForm, setShowOrderForm] = useState(false)

  useEffect(() => {
    if (restaurant) {
      loadMenuItems()
    }
  }, [restaurant])

  const loadMenuItems = async () => {
    try {
      setLoading(true)
      const response = await getMenuItems(restaurant.id)
      setMenuItems(response.data)
      setError(null)
    } catch (err) {
      setError('Failed to load menu: ' + (err.response?.data?.error || err.message))
    } finally {
      setLoading(false)
    }
  }

  const handleQuantityChange = (menuItemId, quantity) => {
    const newItems = [...selectedItems]
    const existingIndex = newItems.findIndex(item => item.menuItemId === menuItemId)
    
    if (quantity > 0) {
      if (existingIndex >= 0) {
        newItems[existingIndex].quantity = quantity
      } else {
        newItems.push({ menuItemId, quantity })
      }
    } else {
      if (existingIndex >= 0) {
        newItems.splice(existingIndex, 1)
      }
    }
    
    onItemsChange(newItems)
  }

  const getQuantity = (menuItemId) => {
    const item = selectedItems.find(item => item.menuItemId === menuItemId)
    return item ? item.quantity : 0
  }

  const totalItems = selectedItems.reduce((sum, item) => sum + item.quantity, 0)

  if (loading) {
    return (
      <div style={{ textAlign: 'center', padding: '50px' }}>
        <Spin size="large" />
      </div>
    )
  }

  if (error) {
    return <Alert message="Error" description={error} type="error" showIcon />
  }

  return (
    <div>
      <Space style={{ marginBottom: '24px' }}>
        <Button icon={<ArrowLeftOutlined />} onClick={onBack}>
          Back to Restaurants
        </Button>
        <Title level={3} style={{ margin: 0 }}>
          {restaurant.name} - Menu
        </Title>
      </Space>

      <Row gutter={[16, 16]}>
        {menuItems.map(item => {
          const quantity = getQuantity(item.id)
          return (
            <Col xs={24} sm={12} lg={8} key={item.id}>
              <Card
                hoverable
                style={{ height: '100%' }}
              >
                <Meta
                  title={item.name}
                  description={
                    <div>
                      <Text type="secondary">{item.description}</Text>
                      <div style={{ marginTop: '8px' }}>
                        <Text strong style={{ fontSize: '18px', color: '#1890ff' }}>
                          ${item.basePrice.toFixed(2)}
                        </Text>
                        <Text type="secondary" style={{ marginLeft: '8px' }}>
                          | Stock: {item.stock}
                        </Text>
                      </div>
                      <div style={{ marginTop: '12px' }}>
                        <Space>
                          <Text>Quantity:</Text>
                          <InputNumber
                            min={0}
                            max={item.stock}
                            value={quantity}
                            onChange={(value) => handleQuantityChange(item.id, value || 0)}
                          />
                        </Space>
                      </div>
                    </div>
                  }
                />
              </Card>
            </Col>
          )
        })}
      </Row>

      {selectedItems.length > 0 && (
        <Card
          style={{ marginTop: '24px', background: '#f0f2f5' }}
        >
          <Space direction="vertical" style={{ width: '100%' }}>
            <Space>
              <Badge count={totalItems} showZero>
                <ShoppingCartOutlined style={{ fontSize: '24px' }} />
              </Badge>
              <Text strong>Selected Items: {totalItems}</Text>
            </Space>
            <Button 
              type="primary" 
              size="large"
              onClick={() => setShowOrderForm(true)}
              block
            >
              Place Order
            </Button>
          </Space>
        </Card>
      )}

      {showOrderForm && (
        <div style={{ marginTop: '24px' }}>
          <OrderForm
            restaurant={restaurant}
            items={selectedItems}
            onOrderPlaced={onPlaceOrder}
            onCancel={() => setShowOrderForm(false)}
          />
        </div>
      )}
    </div>
  )
}

export default MenuView
