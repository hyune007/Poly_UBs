<template>
  <div class="d-flex">
    <Sidebar />

    <div class="flex-grow-1 p-4" style="margin-left:260px; background:#f5f6fa; min-height:100vh;">
      <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2 class="fw-bold fs-3 text-gray-800">📦 Quản lý sản phẩm</h2>
          <router-link class="btn btn-primary shadow" to="/admin/products/form">+ Thêm sản phẩm</router-link>
        </div>

        <!-- Bộ lọc danh mục -->
        <form @submit.prevent="filterProducts" class="mb-4 d-flex flex-wrap gap-2">
          <select class="form-select w-auto" v-model="selectedCategory">
            <option value="">Tất cả danh mục</option>
            <option v-for="c in categories" :key="c.id" :value="c.name">{{ c.name }}</option>
          </select>
          <button class="btn btn-secondary" type="submit">Lọc</button>
        </form>

        <div class="mb-3 text-gray-700">
          <strong>Tổng sản phẩm:</strong>
          <span class="text-primary">{{ totalProducts }}</span>
        </div>

        <div class="table-responsive shadow rounded">
          <table class="table table-hover align-middle mb-0 bg-white">
            <thead class="bg-dark text-white">
            <tr>
              <th>#</th>
              <th>Tên</th>
              <th>Giá</th>
              <th>Danh mục</th>
              <th>Thương hiệu</th>
              <th>Hình ảnh</th>
              <th class="text-center">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="p in products" :key="p.id">
              <td>{{ p.id }}</td>
              <td class="fw-semibold">{{ p.name }}</td>
              <td class="text-danger fw-bold">{{ formatPrice(p.price) }}</td>
              <td>{{ p.category?.name }}</td>
              <td>{{ p.brand?.name }}</td>
              <td>
                <img :src="`http://localhost:8080/photos/${p.image}`"
                     alt="Ảnh"
                     class="img-thumbnail"
                     style="width:60px; height:60px; object-fit:cover;">
              </td>
              <td class="text-center">
                <router-link :to="`/admin/products/form/${p.id}`" class="btn btn-sm btn-warning me-1">✏️</router-link>
                <button class="btn btn-sm btn-danger" @click="deleteProduct(p.id)">🗑️</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Sidebar from '../main-frame-admin/Sidebar.vue';

const products = ref([]);
const categories = ref([]);
const selectedCategory = ref('');
const totalProducts = ref(0);

const API_BASE = 'http://localhost:8080/api';

// Load categories
const loadCategories = async () => {
  try {
    const res = await axios.get(`${API_BASE}/categories`);
    categories.value = res.data;
  } catch (err) {
    console.error(err);
  }
};

// Load products
const loadProducts = async () => {
  try {
    const res = await axios.get(`${API_BASE}/products`);
    let allProducts = res.data;

    // Filter category nếu chọn
    if (selectedCategory.value) {
      allProducts = allProducts.filter(p => p.category.name === selectedCategory.value);
    }

    products.value = allProducts;
    totalProducts.value = allProducts.length;
  } catch (err) {
    console.error(err);
  }
};

// Filter products
const filterProducts = () => {
  loadProducts();
};

// Delete product
const deleteProduct = async (id) => {
  if (!confirm('Xóa sản phẩm này?')) return;
  try {
    await axios.delete(`${API_BASE}/products/${id}`);
    loadProducts();
  } catch (err) {
    console.error(err);
    alert('Xóa thất bại!');
  }
};

// Format giá
const formatPrice = (value) => value.toLocaleString('vi-VN') + ' ₫';

onMounted(() => {
  loadCategories();
  loadProducts();
});
</script>

<style scoped>
/* Optional custom styles */
</style>
