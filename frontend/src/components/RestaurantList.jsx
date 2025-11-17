import React, { useState, useEffect } from 'react'
import { Card, Row, Col, Button, Spin, Alert, Empty } from 'antd'
import { ShopOutlined } from '@ant-design/icons'
import { getRestaurants } from '../api'

const { Meta } = Card

function RestaurantList({ onSelectRestaurant }) {
  const [restaurants, setRestaurants] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    loadRestaurants()
  }, [])

  const loadRestaurants = async () => {
    try {
      setLoading(true)
      const response = await getRestaurants()
      setRestaurants(response.data)
      setError(null)
    } catch (err) {
      setError('Failed to load restaurants: ' + (err.response?.data?.error || err.message))
    } finally {
      setLoading(false)
    }
  }

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

  if (restaurants.length === 0) {
    return <Empty description="No restaurants available" />
  }

  return (
    <Row gutter={[16, 16]}>
      {restaurants.map(restaurant => (
        <Col xs={24} sm={12} lg={8} key={restaurant.id}>
          <Card
            hoverable
            actions={[
              <Button 
                type="primary" 
                onClick={() => onSelectRestaurant(restaurant)}
                block
              >
                View Menu
              </Button>
            ]}
          >
            <Meta
              avatar={<ShopOutlined style={{ fontSize: '24px' }} />}
              title={restaurant.name}
              description={restaurant.address}
            />
          </Card>
        </Col>
      ))}
    </Row>
  )
}

export default RestaurantList
