<template>
  <aside class="sidebar">
    <h3 class="title">Poly_UBs Admin</h3>

    <router-link
        to="/dashboard"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/dashboard') }"
    >
      🏠 Trang chủ
    </router-link>

    <router-link
        to="/admin/customer"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/admin/customer') }"
    >
      👤 Quản lý khách hàng
    </router-link>

    <router-link
        to="/admin/employee"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/admin/employee') }"
    >
      🧑‍💼 Quản lý nhân viên
    </router-link>

    <router-link
        to="/admin/products"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/admin/products') }"
    >
      📦 Quản lý sản phẩm
    </router-link>

    <router-link
        to="/admin/bills/manage"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/admin/bills/manage') }"
    >
      🧾 Quản lý đơn hàng
    </router-link>

    <router-link
        to="/admin/brand"
        class="nav-item"
        :class="{ 'active': $route.path.startsWith('/admin/brand') }"
    >
      🏷️ Quản lý hãng
    </router-link>

    <hr class="divider">

    <!-- Logout button -->
    <button class="nav-item logout" @click="logout">
      🚪 Đăng xuất
    </button>
  </aside>
</template>

<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const logout = async () => {
  try {
    await axios.get('http://localhost:8080/api/auth/logout', {
      withCredentials: true
    });
    window.location.href = 'http://localhost:8080/home'; // redirect sau khi logout
  } catch (err) {
    console.error(err);
  }
};

</script>

<style scoped>
.sidebar {
  height: 100vh;
  width: 240px;
  background: #2f3640;
  color: #fff;
  padding: 20px 10px;
  position: fixed;
  display: flex;
  flex-direction: column;
  gap: 6px;
  border-radius: 0 16px 16px 0;
  box-shadow: 4px 0 12px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 15px;
  font-size: 20px;
  font-weight: bold;
}

.nav-item {
  display: block;
  padding: 12px 15px;
  margin: 4px 6px;
  color: #dcdde1;
  text-decoration: none;
  border-radius: 10px;
  transition: 0.25s ease;
  cursor: pointer;
}

/* Hover đẹp */
.nav-item:hover {
  background: #414b57;
  transform: translateX(4px);
}

/* Active Router Link */
.active {
  background: #1e90ff;
  color: white !important;
  font-weight: bold;
}

/* Dòng phân cách */
.divider {
  margin: 12px 8px;
  border-color: #57606f;
}

/* Logout */
.logout {
  margin-top: auto;
  background: #c23616;
  color: white;
  border: none;
}

.logout:hover {
  background: #e84118;
}
</style>
