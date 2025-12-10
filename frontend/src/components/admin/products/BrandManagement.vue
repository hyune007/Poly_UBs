<template>
  <div class="d-flex">
    <!-- Sidebar -->
    <Sidebar />

    <!-- Main Content -->
    <div class="flex-grow-1 p-4" style="margin-left:260px; background:#f5f6fa; min-height:100vh;">
      <div class="container py-4">
        <h2 class="fw-bold fs-3 mb-4">🏷️ Quản lý thương hiệu</h2>

        <!-- Form thêm/sửa brand -->
        <form @submit.prevent="saveBrand" class="mb-4 d-flex gap-2 align-items-end">
          <div class="flex-grow-1">
            <label class="form-label">ID</label>
            <input type="text" class="form-control" v-model="form.id" :disabled="isEdit" required />
          </div>
          <div class="flex-grow-1">
            <label class="form-label">Tên thương hiệu</label>
            <input type="text" class="form-control" v-model="form.name" required />
          </div>
          <div>
            <button type="submit" class="btn btn-success">{{ isEdit ? 'Cập nhật' : 'Thêm mới' }}</button>
          </div>
          <div v-if="isEdit">
            <button type="button" class="btn btn-secondary" @click="resetForm">Hủy</button>
          </div>
        </form>

        <!-- Danh sách brand -->
        <div class="table-responsive shadow rounded">
          <table class="table table-hover align-middle mb-0 bg-white">
            <thead class="bg-dark text-white">
            <tr>
              <th>#</th>
              <th>Tên thương hiệu</th>
              <th class="text-center">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="b in brands" :key="b.id">
              <td>{{ b.id }}</td>
              <td>{{ b.name }}</td>
              <td class="text-center">
                <button class="btn btn-sm btn-warning me-1" @click="editBrand(b)">✏️</button>
                <button class="btn btn-sm btn-danger" @click="deleteBrand(b.id)">🗑️</button>
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

const API_BASE = 'http://localhost:8080/api/brands';

const brands = ref([]);
const form = ref({ id: '', name: '' });
const isEdit = ref(false);

// Load danh sách brand
const loadBrands = async () => {
  try {
    const res = await axios.get(API_BASE);
    brands.value = res.data;
  } catch (err) {
    console.error(err);
    alert('Không thể load danh sách thương hiệu');
  }
};

// Thêm mới hoặc cập nhật brand
const saveBrand = async () => {
  try {
    if (!form.value.id || !form.value.name) {
      alert('Vui lòng nhập đầy đủ thông tin');
      return;
    }

    if (isEdit.value) {
      // Update
      await axios.put(`${API_BASE}/${form.value.id}`, form.value);
      alert('Cập nhật thành công');
    } else {
      // Create
      await axios.post(API_BASE, form.value);
      alert('Thêm mới thành công');
    }
    resetForm();
    loadBrands();
  } catch (err) {
    console.error(err);
    alert('Lỗi khi lưu thương hiệu');
  }
};

// Xóa brand
const deleteBrand = async (id) => {
  if (!confirm('Xóa thương hiệu này?')) return;
  try {
    await axios.delete(`${API_BASE}/${id}`);
    loadBrands();
  } catch (err) {
    console.error(err);
    alert('Xóa thất bại!');
  }
};

// Chỉnh sửa brand
const editBrand = (b) => {
  form.value = { ...b };
  isEdit.value = true;
};

// Reset form
const resetForm = () => {
  form.value = { id: '', name: '' };
  isEdit.value = false;
};

onMounted(() => {
  loadBrands();
});
</script>

<style scoped>
/* Optional custom styles */
</style>
