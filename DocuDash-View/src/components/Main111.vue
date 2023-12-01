<template>
    <div id="app">
      <nav class="bg-gray-800">
        <div class="container mx-auto flex justify-between">
          <a href="#" class="text-white text-3xl font-bold">ระบบสารบรรณอิเล็กทรอนิกส์ของกรุงเทพมหานคร</a>
          <ul class="flex items-center">
            <li class="p-4">
              <a href="#" class="text-white hover:text-white">ระบบงานสารบรรณอิเล็กทรอนิกส์</a>
            </li>
            <li class="p-4">
              <a href="#" class="text-white hover:text-white">ระบบงานจัดเก็บเอกสารอิเล็กทรอนิกส์</a>
            </li>
            <li class="p-4">
              <a href="#" class="text-white hover:text-white">ระบบบริหารจัดการห้องประชุม</a>
            </li>
            <li class="p-4">
              <a href="#" class="text-white hover:text-white">ระบบหนังสือเวียนอิเล็กทรอนิกส์</a>
            </li>
            <li class="p-4">
              <a href="#" class="text-white hover:text-white">ระบบบริหารงานภายในสำนักงาน</a>
            </li>
          </ul>
        </div>
      </nav>
  
      <div class="container mx-auto py-10">
        <div class="flex flex-wrap justify-center">
          <div class="w-full md:w-3/4">
            <h1 class="text-3xl font-bold">ระบบสารบรรณอิเล็กทรอนิกส์ของกรุงเทพมหานคร</h1>
            <p class="text-lg">ระบบสารสนเทศที่มีประสิทธิภาพในการปฏิบัติงานด้านสารบรรณอิเล็กทรอนิกส์ ช่วยให้การเก็บรักษาและสืบค้นเอกสารได้อย่างสะดวกรวดเร็ว</p>
          </div>
          <div class="w-full md:w-1/4">
            <div class="card">
              <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Logo_Bangkok_Metropolitan_Administration.svg/1200px-Logo_Bangkok_Metropolitan_Administration.svg.png" class="w-full h-full object-cover" alt="โลโก้กรุงเทพมหานคร">
              <div class="card-body">
                <h2 class="text-xl font-bold">กรุงเทพมหานคร</h2>
                <p class="text-base">เมืองหลวงของประเทศไทย</p>
              </div>
            </div>
            <div class="card">
              <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Logo_Thailand.svg/1200px-Logo_Thailand.svg.png" class="w-full h-full object-cover" alt="ธงชาติไทย">
              <div class="card-body">
                <h2 class="text-xl font-bold">ประเทศไทย</h2>
                <p class="text-base">ประเทศในภูมิภาคเอเชียตะวันออกเฉียงใต้</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  
  </script>
  
  <style scoped>
  body {
    background-color: #ffffff;
  }
  
  .container {
    max-width: 1200px;
  }
  
  .nav {
    height: 80px;
  }
  
  .nav a {
    color: #ffffff;
  }
  
  .card {
    width: 250px;
    height: 200px;
    margin: 20px;
    padding: 20px;
    background-color: #ffffff;
  }
  
  .card h2 {
    font-size: 20px;
    font-weight: bold;
  }
  
  .card p {
    font-size: 16px;
  }

  </style>
