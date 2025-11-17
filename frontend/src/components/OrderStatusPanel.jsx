import React, { useState, useEffect } from 'react'
import { Card, Input, Button, Spin, Alert, Empty, Space, Typography, Tag, Timeline, Divider, Badge, Row, Col } from 'antd'
import { SearchOutlined, CheckCircleOutlined, ClockCircleOutlined, CarOutlined, ShopOutlined } from '@ant-design/icons'
import { getOrder, updateOrderStatus, getNotifications } from '../api'

const { Title, Text } = Typography

const statusConfig = {
  PENDING: { color: 'default', icon: <ClockCircleOutlined /> },
  CONFIRMED: { color: 'processing', icon: <CheckCircleOutlined /> },
  PREPARING: { color: 'warning', icon: <ShopOutlined /> },
  ON_THE_WAY: { color: 'processing', icon: <CarOutlined /> },
  DELIVERED: { color: 'success', icon: <CheckCircleOutlined /> },
  CANCELLED: { color: 'error', icon: <ClockCircleOutlined /> }
}

function OrderStatusPanel() {
  const [orderId, setOrderId] = useState('')
  const [order, setOrder] = useState(null)
  const [notifications, setNotifications] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const loadOrder = async () => {
    if (!orderId) return
    
    try {
      setLoading(true)
      setError(null)
      const response = await getOrder(orderId)
      setOrder(response.data)
      
      await loadNotifications(response.data)
    } catch (err) {
      setError('Failed to load order: ' + (err.response?.data?.error || err.message))
      setOrder(null)
    } finally {
      setLoading(false)
    }
  }

  const loadNotifications = async (orderData) => {
    try {
      const allNotifications = []
      
      if (orderData.customerId) {
        const customerNotifs = await getNotifications('CUSTOMER', orderData.customerId)
        allNotifications.push(...customerNotifs.data)
      }
      
      if (orderData.restaurantId) {
        const restaurantNotifs = await getNotifications('RESTAURANT', orderData.restaurantId)
        allNotifications.push(...restaurantNotifs.data)
      }
      
      if (orderData.courierId) {
        const courierNotifs = await getNotifications('COURIER', orderData.courierId)
        allNotifications.push(...courierNotifs.data)
      }
      
      allNotifications.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
      setNotifications(allNotifications.filter(n => n.orderId === orderData.id))
    } catch (err) {
      console.error('Failed to load notifications:', err)
    }
  }

  const handleStatusUpdate = async (newStatus) => {
    try {
      setLoading(true)
      const response = await updateOrderStatus(orderId, newStatus)
      setOrder(response.data)
      await loadNotifications(response.data)
      setError(null)
    } catch (err) {
      setError('Failed to update status: ' + (err.response?.data?.error || err.message))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <Card>
        <Space.Compact style={{ width: '100%', marginBottom: '24px' }}>
          <Input
            placeholder="Enter order ID"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)}
            onPressEnter={loadOrder}
            size="large"
          />
          <Button 
            type="primary" 
            icon={<SearchOutlined />}
            onClick={loadOrder} 
            loading={loading}
            size="large"
          >
            Load Order
          </Button>
        </Space.Compact>

        {error && (
          <Alert
            message="Error"
            description={error}
            type="error"
            showIcon
            style={{ marginBottom: '16px' }}
          />
        )}
      </Card>

      {order && (
        <>
          <Card style={{ marginTop: '16px' }}>
            <Space direction="vertical" style={{ width: '100%' }} size="large">
              <div>
                <Title level={3}>Order #{order.id}</Title>
                <Space wrap>
                  <Tag color={statusConfig[order.status]?.color} icon={statusConfig[order.status]?.icon}>
                    {order.status}
                  </Tag>
                  <Tag>{order.type}</Tag>
                </Space>
              </div>

              <Divider />

              <Row gutter={16}>
                <Col span={12}>
                  <Text type="secondary">Customer:</Text>
                  <div><Text strong>{order.customerName}</Text></div>
                </Col>
                <Col span={12}>
                  <Text type="secondary">Restaurant:</Text>
                  <div><Text strong>{order.restaurantName}</Text></div>
                </Col>
              </Row>

              {order.courierName && (
                <div>
                  <Text type="secondary">Courier:</Text>
                  <div><Text strong>{order.courierName}</Text></div>
                </div>
              )}

              <div>
                <Text type="secondary">Total Price:</Text>
                <div><Text strong style={{ fontSize: '20px', color: '#1890ff' }}>
                  ${order.totalPrice.toFixed(2)}
                </Text></div>
              </div>

              {order.deliveryRoute && (
                <Card 
                  type="inner" 
                  title={
                    <Space>
                      <Text strong>Delivery Route</Text>
                      <Text type="secondary" style={{ fontSize: '12px' }}>
                        (Adapter + Builder Pattern)
                      </Text>
                    </Space>
                  }
                >
                  <Space direction="vertical">
                    <div>
                      <Text type="secondary">Distance: </Text>
                      <Text strong>{order.deliveryRoute.distanceKm.toFixed(2)} km</Text>
                    </div>
                    <div>
                      <Text type="secondary">Estimated Time: </Text>
                      <Text strong>{order.deliveryRoute.estimatedTimeMinutes} minutes</Text>
                    </div>
                    <Text type="secondary">{order.deliveryRoute.routeDetails}</Text>
                  </Space>
                </Card>
              )}
            </Space>
          </Card>

          <Card 
            title="Update Status" 
            style={{ marginTop: '16px' }}
            extra={
              <Text type="secondary" style={{ fontSize: '12px' }}>
                (Observer Pattern - triggers notifications)
              </Text>
            }
          >
            <Space wrap>
              <Button 
                onClick={() => handleStatusUpdate('PREPARING')}
                disabled={loading || order.status === 'PREPARING'}
                loading={loading}
              >
                Mark as Preparing
              </Button>
              <Button 
                onClick={() => handleStatusUpdate('ON_THE_WAY')}
                disabled={loading || order.status === 'ON_THE_WAY'}
                loading={loading}
              >
                Mark as On The Way
              </Button>
              <Button 
                type="primary"
                onClick={() => handleStatusUpdate('DELIVERED')}
                disabled={loading || order.status === 'DELIVERED'}
                loading={loading}
              >
                Mark as Delivered
              </Button>
            </Space>
          </Card>

          <Card 
            title={
              <Space>
                <Text strong>Notifications</Text>
                <Badge count={notifications.length} />
                <Text type="secondary" style={{ fontSize: '12px' }}>
                  (Observer Pattern)
                </Text>
              </Space>
            }
            style={{ marginTop: '16px' }}
          >
            {notifications.length === 0 ? (
              <Empty description="No notifications yet. Update order status to see notifications." />
            ) : (
              <Timeline>
                {notifications.map(notif => (
                  <Timeline.Item
                    key={notif.id}
                    color={notif.recipientType === 'CUSTOMER' ? 'blue' : 
                           notif.recipientType === 'RESTAURANT' ? 'orange' : 'green'}
                  >
                    <Space direction="vertical" size="small">
                      <div>
                        <Tag color={
                          notif.recipientType === 'CUSTOMER' ? 'blue' : 
                          notif.recipientType === 'RESTAURANT' ? 'orange' : 'green'
                        }>
                          {notif.recipientType}
                        </Tag>
                        <Text>{notif.message}</Text>
                      </div>
                      <Text type="secondary" style={{ fontSize: '12px' }}>
                        {new Date(notif.createdAt).toLocaleString()}
                      </Text>
                    </Space>
                  </Timeline.Item>
                ))}
              </Timeline>
            )}
          </Card>
        </>
      )}

      {!order && !loading && orderId && (
        <Empty description="No order found with this ID" />
      )}
    </div>
  )
}

export default OrderStatusPanel
