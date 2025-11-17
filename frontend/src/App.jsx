import React, { useState } from 'react'
import { Layout, Typography, Tabs, Alert } from 'antd'
import RestaurantList from './components/RestaurantList'
import MenuView from './components/MenuView'
import OrderForm from './components/OrderForm'
import OrderStatusPanel from './components/OrderStatusPanel'
import './App.css'

const { Header, Content } = Layout
const { Title, Paragraph } = Typography

function App() {
  const [view, setView] = useState('restaurants')
  const [selectedRestaurant, setSelectedRestaurant] = useState(null)
  const [selectedItems, setSelectedItems] = useState([])

  const tabItems = [
    {
      key: 'restaurants',
      label: 'Restaurants & Menu',
      children: !selectedRestaurant ? (
        <RestaurantList 
          onSelectRestaurant={(restaurant) => setSelectedRestaurant(restaurant)}
        />
      ) : (
        <div>
          <MenuView 
            restaurant={selectedRestaurant}
            selectedItems={selectedItems}
            onItemsChange={setSelectedItems}
            onPlaceOrder={() => {
              setSelectedRestaurant(null)
              setSelectedItems([])
              setView('orders')
            }}
            onBack={() => {
              setSelectedRestaurant(null)
              setSelectedItems([])
            }}
          />
        </div>
      )
    },
    {
      key: 'orders',
      label: 'Order Status & Notifications',
      children: <OrderStatusPanel />
    }
  ]

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header style={{ background: '#001529', padding: '0 24px' }}>
        <Title level={2} style={{ color: '#fff', margin: '16px 0' }}>
          🍕 Food Delivery Platform
        </Title>
      </Header>
      <Content style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto', width: '100%' }}>
        <Alert
          message="Design Patterns Demonstration"
          description="This application demonstrates 9 design patterns: Factory Method, Builder, Abstract Factory, Facade, Adapter, Decorator, Strategy, Observer, and Chain of Responsibility"
          type="info"
          showIcon
          style={{ marginBottom: '24px' }}
        />
        <Tabs
          activeKey={view}
          onChange={setView}
          items={tabItems}
          size="large"
        />
      </Content>
    </Layout>
  )
}

export default App
