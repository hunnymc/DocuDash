<script setup>

</script>

<template>
<section class="bg-gray-200 shadow-md sm:rounded-lg w-full">
  <div class="py-4 px-4 mx-auto max-w-2xl lg:py-16">
      <h2 class="mb-4 text-3xl font-bold text-gray-900 ">ตั้งค่ารหัสผ่านใหม่</h2>
      <form action="#">
          <div class="grid gap-4 sm:grid-cols-2 sm:gap-6">
              <!-- <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">ชื่อผู้ใช้ (Username)</label>
                  <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกชื่อผู้ใช้" required="">
              </div> -->
              
            <br>

              <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">ชื่อ - นามสกุล </label>
                  <div type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกชื่อ - นามสกุล" required="">
                  <h3 class="text-sm font-bold text-gray-900 dark:text-white ">ธนพนธ์ สุขเกษม</h3>
                </div>
              </div>
              <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">รหัสผ่านเดิม (Old password)</label>
                  <input type="password" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกรหัสผ่านเดิม" required="">
              </div>

              <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-light text-gray-900 "> * รหัสผ่านของท่านจะต้องเป็นตัวอักษรภาษาอังกฤษและตัวเลขไม่ต่ำกว่า6ตัวอักษร *</label>
                  
              </div>

              <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">รหัสผ่านใหม่ (New password)</label>
                  <input type="password" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกรหัสผ่านใหม่" required="">
              </div>
              <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">ยืนยันรหัสผ่านใหม่ (Confirm New password)</label>
                  <input type="password" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="กรอกรหัสผ่านใหม่อีกครั้ง" required="">
              </div>
              

              <!-- <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 ">อีเมล</label>
                  <input type="email" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="example@mail.com" required="">
              </div> -->

              <!-- <div class="w-full">
                  <label for="brand" class="block mb-2 text-sm font-medium text-gray-900 ">Brand</label>
                  <input type="text" name="brand" id="brand" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                  placeholder="Product brand" required="">
              </div>
              <div class="w-full">
                  <label for="price" class="block mb-2 text-sm font-medium text-gray-900 ">Price</label>
                  <input type="number" name="price" id="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="$2999" required="">
              </div> -->

              
              <!-- <div  class="sm:col-span-2">
                  <label for="category" class="block mb-2 text-sm font-medium text-gray-900 ">แผนก</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                <option selected="">เลือกแผนกของผู้ใช้ </option>
                <option value="ฝ่ายเอกสาร">ฝ่ายเอกสาร</option>
                <option value="ฝ่ายการตลาด">ฝ่ายการตลาด</option>
                <option value="ฝ่ายขาย">ฝ่ายขาย</option>
                <option value="ฝ่ายบุคคล">ฝ่ายบุคคล</option>
                <option value="ฝ่ายลูกค้าสัมพันธ์">ฝ่ายลูกค้าสัมพันธ์</option>
                <option value="ฝ่ายบัญชี/การเงิน">ฝ่ายบัญชี/การเงิน</option>
                <option value="ฝ่ายจัดซื้อ">ฝ่ายจัดซื้อ</option>
                <option value="ฝ่าย IT">ฝ่าย IT</option>
                <option value="อื่นๆ">อื่นๆ</option>
                  </select>
              </div>

              <div class="sm:col-span-2">
                  <label for="category" class="block mb-2 text-sm font-medium text-gray-900 "> บทบาท (Role)</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                      <option selected="">เลือก Role ของผู้ใช้</option>
                      <option value="TV">Admin</option>
                      <option value="PC">User</option>
                      <option value="GA">Manager</option>
                  </select>
              </div>

              -->

              <!-- <div class="sm:col-span-2">
                  <label for="description" class="block mb-2 text-sm font-medium text-gray-900 ">Description</label>
                  <textarea id="description" rows="8" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
              </div> -->
          </div>
          <a type="submit" class="cursor-pointer flex-col justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200">
              บันทึก
          </a>
      </form>
  </div>
  
</section>
</template>

<style scoped>

</style>