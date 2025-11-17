import axios from 'axios'

// Use proxy in development, direct URL in production
const API_BASE_URL = import.meta.env.VITE_API_URL || ''

const api = axios.create({
  baseURL: API_BASE_URL || (import.meta.env.DEV ? '' : 'http://localhost:8080'),
  headers: {
    'Content-Type': 'application/json',
  },
})

export const getRestaurants = () => api.get('/api/restaurants')
export const getMenuItems = (restaurantId) => api.get(`/api/restaurants/${restaurantId}/menu-items`)
export const getCustomers = () => api.get('/api/customers')
export const createOrder = (orderData) => api.post('/api/orders', orderData)
export const getOrder = (orderId) => api.get(`/api/orders/${orderId}`)
export const updateOrderStatus = (orderId, status) => api.patch(`/api/orders/${orderId}/status`, { status })
export const getNotifications = (recipientType, recipientId) => 
  api.get('/api/notifications', { params: { recipientType, recipientId } })

export default api

