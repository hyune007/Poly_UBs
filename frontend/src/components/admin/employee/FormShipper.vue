<template>
  <div class="container my-4">
    <h1 class="mb-3">Form xác nhận giao hàng (Shipper)</h1>
    <p class="lead">Ghi nhận trạng thái giao hàng, thông tin người nhận và ghi chú.</p>
    <router-link to="/shipper/manage" class="btn btn-secondary btn-sm mb-3">Quay lại</router-link>

    <div v-if="bills.length === 0" class="alert alert-info">
      Không có hóa đơn cần giao.
    </div>

    <div v-for="bill in bills" :key="bill.id" class="card mb-4 shadow-sm">
      <div class="card-header bg-primary text-white">
        Hóa đơn {{ bill.id }}
      </div>
      <div class="card-body">
        <form @submit.prevent="updateStatus(bill.id)">
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Số hóa đơn *</label>
              <input type="text" class="form-control" :value="bill.id" readonly />
            </div>
            <div class="col-md-6">
              <label class="form-label">Tên shipper</label>
              <input type="text" class="form-control" :value="bill.employee?.name || 'Chưa gán'" readonly />
            </div>
            <div class="col-md-6">
              <label class="form-label">Thời gian giao *</label>
              <input v-model="bill.deliveredAt" type="datetime-local" class="form-control" required />
            </div>
            <div class="col-md-6">
              <label class="form-label">Trạng thái đơn hàng</label>
              <select v-model="bill.status" class="form-select">
                <option value="Đang giao">Đang giao</option>
                <option value="Đã giao thành công">Đã giao thành công</option>
                <option value="Giao không thành công - Đã hoàn hàng về cho shop">Giao không thành công - Đã hoàn hàng về cho shop</option>
              </select>
            </div>
            <div class="col-md-6">
              <label class="form-label">Tên người nhận *</label>
              <input type="text" class="form-control" :value="bill.customer.name" readonly />
            </div>
            <div class="col-md-6">
              <label class="form-label">Số điện thoại người nhận</label>
              <input type="tel" class="form-control" :value="bill.customer.phone" readonly />
            </div>
            <div class="col-12">
              <label class="form-label">Địa chỉ người nhận</label>
              <input type="text" class="form-control" :value="bill.address.fullAddress" readonly />
            </div>
            <div class="col-12">
              <label class="form-label">Ghi chú thêm</label>
              <textarea v-model="bill.notes" class="form-control" placeholder="Ghi chú"></textarea>
            </div>
          </div>

          <hr>
          <h5>Chi tiết đơn hàng</h5>
          <div class="table-responsive">
            <table class="table table-striped table-bordered table-hover table-sm mb-0">
              <thead class="table-primary">
              <tr>
                <th>Sản phẩm</th>
                <th>Số lượng</th>
                <th>Tổng</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="d in bill.billDetails" :key="d.id">
                <td>{{ d.product?.name || '—' }}</td>
                <td>{{ d.quantity }}</td>
                <td>{{ d.total }}</td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="mt-3 d-flex justify-content-between">
            <button type="reset" class="btn btn-secondary">Reset</button>
            <button type="submit" class="btn btn-primary">Xác nhận & Gửi</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const bills = ref([]);

const fetchBills = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/bills');
    // Chỉ lấy hóa đơn chưa giao
    bills.value = res.data.filter(b => b.status !== 'Đã giao thành công');
    // Thêm trường deliveredAt và notes để binding
    bills.value.forEach(b => { b.deliveredAt = ''; b.notes = ''; });
  } catch (error) {
    console.error(error);
  }
};

const updateStatus = async (billId) => {
  const bill = bills.value.find(b => b.id === billId);
  if (!bill) return;

  try {
    await axios.post('/api/shipper/updateStatus', {
      billId: bill.id,
      customerId: bill.customer.id,
      deliveredAt: bill.deliveredAt,
      status: bill.status,
      notes: bill.notes
    });
    alert('Cập nhật hóa đơn thành công!');
    fetchBills();
  } catch (error) {
    console.error(error);
    alert('Cập nhật hóa đơn thất bại!');
  }
};

onMounted(fetchBills);
</script>
