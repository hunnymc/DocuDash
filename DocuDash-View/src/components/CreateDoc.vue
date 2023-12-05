<script setup>

</script>
 
<template>
    <div class="shadow-md sm:rounded-lg w-full ">

        <section class="bg-gray-200 dark:bg-gray-900 ">
            
         <div class="py-8 px-auto ml-5 mr-auto  lg:py-12 w-full  max-width: 100%  justify-center ">
                 <h2 class="mb-4 text-4xl font-bold text-gray-900 dark:text-white">สร้างเอกสารใหม่ 
                    <button class="m-1 mb-4 pb-4"  data-popover-target="popover-description" data-popover-placement="bottom-end" type="button">
              <svg class="w-4 h-4 ms-2 text-gray-400 hover:text-gray-500" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z" clip-rule="evenodd"></path></svg><span class="sr-only">Show information</span></button>
                  <div data-popover id="popover-description" role="tooltip" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
                  <div class="p-3 space-y-2">
        <h3 class="font-semibold text-gray-900 dark:text-white">การสร้างเอกสาร</h3>
        <p>ท่านสามารถสร้างเอกสารที่ต้องการโดยกรอกรายละเอียดเอกสารต่าง ๆ ลงในแบบฟอร์ม แนบไฟล์เอกสาร และกดยืนยันเพื่อทำการส่ง</p>
        <h3 class="font-semibold text-gray-900 dark:text-white">การเลือกผู้ส่ง</h3>
        <p>ท่านสามารถเลือกผู้คนที่ต้องการจะส่งถึงได้ หากเลือกผิดสามารถทำการลบหรือเพิ่มในภายหลังได้ที่หน้าแรก</p>
        <!-- <a href="#" class="flex items-center font-medium text-blue-600 dark:text-blue-500 dark:hover:text-blue-600 hover:text-blue-700 hover:underline">Read more <svg class="w-2 h-2 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
  </svg></a> -->
    </div>
    <div data-popper-arrow></div>
</div></h2>

                <form action="#">
              <div class="grid gap-4 grid-rows-8 sm:grid-cols-2 sm:gap-6">
              <!-- <div class="sm:col-span-2">
                  <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Name</label>
                  <input type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type product name" required="">
              </div> -->
              <div class="w-full">
                  <label  class=" block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลขที่ :</label>
                  <input  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w- p-1 dark:bg-gray-700  " placeholder="เลขที่เอกสาร" >
              </div>
              <br>
              <div class="w-full">
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เรื่อง :</label>
                  <input type="text"  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                  focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 " placeholder="กรุณากรอกชื่อเรื่องของเอกสาร" required="">
              </div>
              <div class="w-full ">
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">จาก</label>
                  <input type="text"  id="brand" class=" bg-gray-50 border border-gray-300
                   text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 " placeholder="กรุณากรอกชื่อผู้ส่ง" required="">
              </div>
              <div class="w-full">
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">รายละเอียด</label>
                  <input  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 " placeholder="กรอกรายละเอียดภายในเอกสารเบื้องต้น" >
              </div>
              <div>
                  <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">แผนก</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700 ">
                      <option selected="">เลือกแผนกของผู้ส่ง</option>
                      <option value="TV">AAAA</option>
                      <option value="PC">BBBB</option>
                      <option value="GA">CCCC</option>
                      <option value="PH">DDDD</option>
                  </select>
              </div>
              <div class="w-full">
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">โทรศัพท์</label>
                  <input type="number"  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 " placeholder="08X-XXX-XXXX" >
              </div>
              <div class="w-full">
                  <label  class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">อีเมล</label>
                  <input type="email"  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 " placeholder="Example@xmail.com" >
              </div>
              <!-- หมวดหมู่ -->
              <div>
                  <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมวดหมู่</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700 ">
                      <option selected="">เลือกหมวดหมู่เอกสาร</option>
                      <option value="TV">AAAA</option>
                      <option value="PC">BBBB</option>
                      <option value="GA">CCCC</option>
                      <option value="PH">DDDD</option>
                  </select>
              </div>
              <br>
              <div>
                  <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ความเร่งด่วน</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700 ">
                      <option selected="">เลือกความเร่งด่วนของเอกสาร</option>
                      <option value="TV">AAAA</option>
                      <option value="PC">BBBB</option>
                      <option value="GA">CCCC</option>
                      <option value="PH">DDDD</option>
                  </select>
              </div>
              <br>
              <div>
                  <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">ชั้นความลับ</label>
                  <select id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-5/6 p-2.5 dark:bg-gray-700 ">
                      <option selected="">เลือกชั้นความลับของเอกสาร</option>
                      <option value="TV">AAAA</option>
                      <option value="PC">BBBB</option>
                      <option value="GA">CCCC</option>
                      <option value="PH">DDDD</option>
                  </select>
              </div>
              <br>
              <!-- <div>
                  <label for="item-weight" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">Item Weight (kg)</label>
                  <input type="number" name="item-weight" id="item-weight" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-5/6 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="12" required="">
              </div>  -->

              <!-- เลือกผู้ส่ง -->
              <div class="w-full">
                <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ส่ง</label>
                <ul class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
         <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="vue-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="vue-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา ฟ้าสวย</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
        </div>
    </li>
    <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="react-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="react-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย นุ่มมิ่ม</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
        </div>
    </li>
    <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="angular-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา นาปี</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายผลิต</label>
        </div>
    </li>
</ul>
              </div> 
              <!-- ผู้ลงนาม -->
              <div class="w-full">
                <label for="category" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">เลือกผู้ลงนาม</label>
                <ul class="w-5/6 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
         <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="vue-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="vue-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">นภา ฟ้าสวย</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">งานเอกสาร</label>
        </div>
    </li>
    <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="react-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="react-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">ใบฝ้าย นุ่มมิ่ม</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายขาย</label>
        </div>
    </li>
    <li class="w-full hover:bg-gray-50 border-b border-gray-200 rounded-t-lg dark:border-gray-600">
        <div class="flex items-center ps-3">
            <input id="angular-checkbox" type="checkbox" value="" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-gray-900 dark:text-gray-300">กันยา นาปี</label>
            <label for="angular-checkbox" class="w-full py-3 ms-2 text-sm font-bold text-orange-400 dark:text-gray-300">ฝ่ายผลิต</label>
            </div>
            </li>
             </ul>
           </div> 
           
           <!-- กล่องวางไฟล์ -->
           <div class="flex items-center  w-full">
         <label for="dropzone-file" class="flex flex-col items-center justify-center 
         w-5/6 h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 
         dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
        <div class="flex flex-col items-center justify-center pt-5 pb-6 ">
            <svg class="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
            </svg>
            <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">คลิ๊กเพื่ออัพโหลดเอกสาร</span> หรือลากไฟล์และวาง</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG, or Docx (MAX 10 MB)</p>
        </div>
        <input id="dropzone-file" type="file" class="hidden" />
        </label>
        </div> 

            <!-- ที่แสดงไฟล์ -->
             <div class="w-full">
                <div class="  rounded-lg shadow-md w-5/6">
                <table class="rounded-lg w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                 <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                  <tr>
               
                <th scope="col" class="px-6 py-3">
                    ชื่อไฟล์
                </th>
                          
                <th scope="col" class="px-6 py-3">
                    ขนาด
                </th>
                <th scope="col" class=" py-3">
                    
                </th>
            </tr>
        </thead>
        <tbody>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    เอกสารลับมากๆ
                </td>
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    46.6 MB
                </td>
                <td class=" py-4">
                    <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline">ลบ</a>
                </td>
            </tr>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    ไก่ย่าง
                </td>
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    7 MB
                </td>
                
                <td class=" py-4">
                    <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline">ลบ</a>
                </td>
            </tr>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    Hack IPhone 12 
                </td>
                <td class="px-6 py-4 font-semibold text-gray-900 dark:text-white">
                    10 MB
                </td>
               
                <td class=" py-4">
                    <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline">ลบ</a>
                </td>
            </tr>
        </tbody>
    </table>
</div>
              </div>  


            
              <div class="w-full">
                  <label for="description" class="block mb-2 text-sm font-bold text-gray-900 dark:text-white">หมายเหตุ</label>
                  <textarea   class="block p-2.5 w-5/6 text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="กรอกหมายเหตุของคุณ"></textarea>
              </div>              
          </div>
          <br>
          
          <button type="submit" class=" flex-col  justify-center hover:bg-green-800 items-center px-16 py-4 mt-4 sm:mt-6 text-sm font-bold text-white text-center bg-green-600 rounded-lg focus:ring-4 focus:ring-primary-200 ">
              ยืนยัน
          </button>
      </form>
  </div>
</section>
    </div>
</template>
 
<style>

</style>