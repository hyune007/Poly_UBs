<template>
  <div class="d-flex">
    <Sidebar />
    <div class="flex-grow-1 p-4" style="margin-left:260px; background:#f5f6fa; min-height:100vh;">
      <div class="container py-4">
        <h2 class="fw-bold fs-3 mb-4">{{ isEdit ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm mới' }}</h2>

        <form @submit.prevent="saveProduct">
          <div class="mb-3">
            <label>ID</label>
            <input v-model="product.id" type="text" class="form-control" placeholder="Nhập ID sản phẩm" :readonly="isEdit" required/>
          </div>

          <div class="mb-3">
            <label>Tên sản phẩm</label>
            <input v-model="product.name" type="text" class="form-control" required/>
          </div>

          <div class="mb-3">
            <label>Giá</label>
            <input v-model.number="product.price" type="number" class="form-control" required/>
          </div>

          <div class="mb-3">
            <label>Stock</label>
            <input v-model.number="product.stock" type="number" class="form-control" required/>
          </div>

          <div class="mb-3">
            <label>Mô tả</label>
            <textarea v-model="product.description" class="form-control" rows="3"></textarea>
          </div>

          <div class="mb-3">
            <label>Hình ảnh (URL)</label>
            <input v-model="product.image" type="text" class="form-control"/>
          </div>

          <div class="mb-3">
            <label>Thương hiệu</label>
            <select v-model="product.brand.id" class="form-select" required>
              <option value="">-- Chọn thương hiệu --</option>
              <option v-for="b in brands" :key="b.id" :value="b.id">{{ b.name }}</option>
            </select>
          </div>

          <div class="mb-3">
            <label>Danh mục</label>
            <select v-model="product.category.id" class="form-select" required>
              <option value="">-- Chọn danh mục --</option>
              <option v-for="c in categories" :key="c.id" :value="c.id">{{ c.name }}</option>
            </select>
          </div>

          <div class="d-flex justify-content-between">
            <router-link class="btn btn-secondary" to="/admin/products">← Quay lại</router-link>
            <button class="btn btn-success" type="submit">Lưu sản phẩm</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import Sidebar from '../main-frame-admin/Sidebar.vue';

const route = useRoute();
const router = useRouter();
const API_BASE = 'http://localhost:8080/api';

const product = ref({
  id: '',
  name: '',
  price: 0,
  stock: 0,
  description: '',
  image: '',
  brand: { id: '' },
  category: { id: '' }
});

const brands = ref([]);
const categories = ref([]);

const isEdit = computed(() => !!route.params.id);

const loadBrands = async () => {
  try { const res = await axios.get(`${API_BASE}/brands`); brands.value = res.data; } catch(err){ console.error(err); }
};
const loadCategories = async () => {
  try { const res = await axios.get(`${API_BASE}/categories`); categories.value = res.data; } catch(err){ console.error(err); }
};

const loadProduct = async (id) => {
  try {
    const res = await axios.get(`${API_BASE}/products/${id}`);
    product.value = res.data;
  } catch(err) {
    console.error(err);
    alert('Không tải được thông tin sản phẩm!');
  }
};

const saveProduct = async () => {
  try {
    if (isEdit.value) {
      await axios.put(`${API_BASE}/products/${product.value.id}`, product.value);
    } else {
      await axios.post(`${API_BASE}/products`, product.value);
    }
    alert('Lưu sản phẩm thành công!');
    router.push('/admin/products');
  } catch(err) {
    console.error(err);
    alert('Lưu sản phẩm thất bại!');
  }
};

onMounted(() => {
  loadBrands();
  loadCategories();
  if (isEdit.value) loadProduct(route.params.id);
});
</script>
