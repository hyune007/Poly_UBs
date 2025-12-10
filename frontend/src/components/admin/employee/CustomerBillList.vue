<template>
  <div class="d-flex">
    <!-- Sidebar -->
    <Sidebar />

    <!-- Nội dung chính -->
    <div class="flex-grow-1 p-4" style="margin-left: 240px;">
      <div class="page-card">
        <div class="page-header d-flex justify-content-between align-items-center mb-3">
          <div class="d-flex align-items-center gap-3">
            <div class="brand-badge bg-primary text-white px-2 py-1 rounded">KH</div>
            <div>
              <h5 class="mb-0">Khách hàng có hóa đơn</h5>
              <div class="muted">Danh sách khách hàng đã tạo hóa đơn</div>
            </div>
          </div>
          <div class="d-flex align-items-center gap-2">
            <input v-model="searchQuery" type="search" class="form-control form-control-sm" placeholder="Tìm theo tên / id / điện thoại / email" />
            <router-link to="/admin/dashboard" class="btn btn-outline-secondary btn-sm">Quay lại</router-link>
          </div>
        </div>

        <div v-if="!customers.length" class="py-4">
          <div class="alert alert-info mb-0">Không có khách hàng có hóa đơn.</div>
        </div>

        <div v-else class="table-card mt-3 table-responsive">
          <table class="table table-custom mb-0 align-middle">
            <thead>
            <tr>
              <th>KH ID</th>
              <th>Tên</th>
              <th>Điện thoại</th>
              <th>Email</th>
              <th class="text-end pe-4">Hành động</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="c in filteredCustomers" :key="c.id">
              <td>{{ c.id }}</td>
              <td>{{ c.name }}</td>
              <td>{{ c.phone }}</td>
              <td>{{ c.email }}</td>
              <td class="text-end">
                <router-link :to="`/admin/bills/customer/${c.id}`" class="btn btn-primary btn-sm w-100">Xem hóa đơn</router-link>
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
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import Sidebar from "../main-frame-admin/Sidebar.vue";

const customers = ref([]);
const searchQuery = ref('');

const filteredCustomers = computed(() => {
  const q = searchQuery.value.trim().toLowerCase();
  if (!q) return customers.value;
  return customers.value.filter(c =>
      [c.id, c.name, c.phone, c.email].some(field => field && field.toLowerCase().includes(q))
  );
});

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/bills');
    const bills = res.data || [];
    // Lấy danh sách khách hàng duy nhất từ bills
    const uniqueCustomers = {};
    bills.forEach(b => {
      if (b.customer) uniqueCustomers[b.customer.id] = b.customer;
    });
    customers.value = Object.values(uniqueCustomers);
  } catch (err) {
    console.error(err);
  }
});
</script>

<style scoped>
.page-card {
  background: white;
  border-radius: 12px;
  padding: 1rem;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}

.page-header .brand-badge {
  font-weight: bold;
}
</style>
