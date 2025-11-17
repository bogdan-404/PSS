import React, { useState, useEffect } from 'react'
import { Card, Form, Select, Radio, Checkbox, DatePicker, Button, Alert, Space, Typography, Divider } from 'antd'
import { getCustomers, createOrder } from '../api'
import dayjs from 'dayjs'

const { Title, Text } = Typography
const { Option } = Select

function OrderForm({ restaurant, items, onOrderPlaced, onCancel }) {
  const [form] = Form.useForm()
  const [customers, setCustomers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(null)
  const [orderType, setOrderType] = useState('STANDARD')

  useEffect(() => {
    loadCustomers()
  }, [])

  const loadCustomers = async () => {
    try {
      const response = await getCustomers()
      setCustomers(response.data)
      if (response.data.length > 0) {
        form.setFieldsValue({ customerId: response.data[0].id })
      }
    } catch (err) {
      setError('Failed to load customers: ' + err.message)
    }
  }

  const handleSubmit = async (values) => {
    setLoading(true)
    setError(null)
    setSuccess(null)

    try {
      const orderData = {
        customerId: values.customerId,
        restaurantId: restaurant.id,
        orderType: values.orderType,
        items: items.map(item => ({
          menuItemId: item.menuItemId,
          quantity: item.quantity
        })),
        paymentMethod: values.paymentMethod,
        priority: values.priority || false,
        extraPackaging: values.extraPackaging || false,
        insurance: values.insurance || false,
        scheduledFor: values.orderType === 'SCHEDULED' && values.scheduledFor 
          ? values.scheduledFor.format('YYYY-MM-DDTHH:mm:ss')
          : null
      }

      const response = await createOrder(orderData)
      setSuccess(`Order #${response.data.id} created successfully! Total: $${response.data.totalPrice.toFixed(2)}`)
      
      setTimeout(() => {
        onOrderPlaced(response.data)
      }, 2000)
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create order: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card>
      <Title level={3}>Place Order</Title>
      <Form
        form={form}
        layout="vertical"
        onFinish={handleSubmit}
        initialValues={{
          orderType: 'STANDARD',
          paymentMethod: 'STRIPE',
          priority: false,
          extraPackaging: false,
          insurance: false
        }}
      >
        <Form.Item
          label="Customer"
          name="customerId"
          rules={[{ required: true, message: 'Please select a customer' }]}
        >
          <Select placeholder="Select customer">
            {customers.map(customer => (
              <Option key={customer.id} value={customer.id}>
                {customer.name} - {customer.email}
              </Option>
            ))}
          </Select>
        </Form.Item>

        <Form.Item
          label={
            <Space>
              <Text strong>Order Type</Text>
              <Text type="secondary" style={{ fontSize: '12px' }}>
                (Factory Method Pattern)
              </Text>
            </Space>
          }
          name="orderType"
          rules={[{ required: true }]}
        >
          <Radio.Group 
            onChange={(e) => setOrderType(e.target.value)}
            buttonStyle="solid"
          >
            <Radio.Button value="STANDARD">Standard</Radio.Button>
            <Radio.Button value="SCHEDULED">Scheduled</Radio.Button>
            <Radio.Button value="GROUP">Group</Radio.Button>
          </Radio.Group>
        </Form.Item>

        {orderType === 'SCHEDULED' && (
          <Form.Item
            label="Scheduled For"
            name="scheduledFor"
            rules={[{ required: true, message: 'Please select scheduled time' }]}
          >
            <DatePicker
              showTime
              format="YYYY-MM-DD HH:mm"
              style={{ width: '100%' }}
              disabledDate={(current) => current && current < dayjs().startOf('day')}
            />
          </Form.Item>
        )}

        <Divider orientation="left">
          <Text strong>Extras</Text>
          <Text type="secondary" style={{ fontSize: '12px', marginLeft: '8px' }}>
            (Decorator Pattern)
          </Text>
        </Divider>

        <Form.Item name="priority" valuePropName="checked">
          <Checkbox>Priority Delivery (+$5.00)</Checkbox>
        </Form.Item>

        <Form.Item name="extraPackaging" valuePropName="checked">
          <Checkbox>Extra Packaging (+$1.50 per item)</Checkbox>
        </Form.Item>

        <Form.Item name="insurance" valuePropName="checked">
          <Checkbox>Insurance (+3% of order)</Checkbox>
        </Form.Item>

        <Divider orientation="left">
          <Text strong>Payment Method</Text>
          <Text type="secondary" style={{ fontSize: '12px', marginLeft: '8px' }}>
            (Abstract Factory Pattern)
          </Text>
        </Divider>

        <Form.Item
          name="paymentMethod"
          rules={[{ required: true, message: 'Please select payment method' }]}
        >
          <Select>
            <Option value="STRIPE">Stripe</Option>
            <Option value="PAYPAL">PayPal</Option>
            <Option value="CASH_ON_DELIVERY">Cash on Delivery</Option>
          </Select>
        </Form.Item>

        {error && (
          <Alert
            message="Error"
            description={error}
            type="error"
            showIcon
            style={{ marginBottom: '16px' }}
          />
        )}

        {success && (
          <Alert
            message="Success"
            description={success}
            type="success"
            showIcon
            style={{ marginBottom: '16px' }}
          />
        )}

        <Form.Item>
          <Space>
            <Button type="primary" htmlType="submit" loading={loading} size="large">
              Place Order
            </Button>
            <Button onClick={onCancel} size="large">
              Cancel
            </Button>
          </Space>
        </Form.Item>
      </Form>
    </Card>
  )
}

export default OrderForm
