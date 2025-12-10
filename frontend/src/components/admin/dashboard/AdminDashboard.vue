<template>
  <div class="d-flex">
    <!-- Sidebar dùng lại Sidebar.vue -->
    <Sidebar />

    <div class="admin-content p-4 flex-grow-1">
      <h2 class="mb-4">Bảng điều khiển quản trị</h2>
      <p>Xin chào, <strong>Admin</strong> 👋</p>

      <div class="row mt-4">
        <div class="col-md-4" v-for="item in dashboard" :key="item.title">
          <div class="card shadow-sm admin-card">
            <div class="card-body text-center">
              <h5>{{ item.title }}</h5>
              <p class="text-muted">{{ item.subtitle }}</p>

              <!-- Nếu item.link có giá trị thì dùng router-link, nếu không dùng button thường -->
              <router-link
                  v-if="item.link"
                  :to="item.link"
                  class="btn btn-primary"
              >
                {{ item.button }}
              </router-link>
              <button v-else class="btn btn-primary">
                {{ item.button }}
              </button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Sidebar from "../main-frame-admin/Sidebar.vue";

const dashboard = ref([
  { title: "Khách hàng", subtitle: "Xem và quản lý danh sách khách hàng", button: "Quản lý", link: "/admin/customer" },
  { title: "Nhân viên", subtitle: "Quản lý thông tin và quyền của nhân viên", button: "Quản lý", link: "/admin/employee" },
  { title: "Sản phẩm", subtitle: "Thêm, sửa, xóa và xem sản phẩm", button: "Quản lý", link: "/admin/products" },
  { title: "Đơn hàng", subtitle: "Xem và quản lý các đơn hàng", button: "Quản lý", link: "/admin/bills/manage" },
  { title: "Hãng", subtitle: "Quản lý các hãng sản phẩm", button: "Quản lý", link: "/admin/brand" },
]);
</script>

<style scoped>
.admin-card {
  border-radius: 12px;
  transition: 0.2s;
}
.admin-card:hover {
  transform: translateY(-3px);
  background: #f8fafc;
}
</style>