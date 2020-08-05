 $(document).ready(function() {
 
 'use strict';

     Morris.Donut({
         element: 'case-graph-donut',
         data: [
             //{value: 0, label: '凶杀', formatted: '26%' },
             //{value: 0, label: '强奸', formatted: '14%' },
             //{value: 0, label: '抢劫抢夺', formatted: '19%' },
             //{value: 0, label: '侵财', formatted: '30%' },
             //{value: 0, label: '其他', formatted: '11%' }
         ],
         backgroundColor: '#fff',
         labelColor: '#2c99b9',
         colors: [
             '#f8d347','#ff6c60','#fcf8e3','#a659fc','#278ba7'
         ],
         formatter: function (x, data) { return data.formatted; }
     });

     Morris.Donut({
         element: 'sample-graph-donut',
         data: [
             //{value: 33, label: '血痕', formatted: '33%' },
             //{value: 17, label: '唾液斑', formatted: '17%' },
             //{value: 22, label: '精斑', formatted: '22%' },
             //{value: 18, label: '脱落细胞', formatted: '18%' },
             //{value: 10, label: '毛发', formatted: '10%' }
         ],
         backgroundColor: '#fff',
         labelColor: '#2c99b9',
         colors: [
             '#ff6c60','#55ACEE','#93c059','#a659fc','#f8d347'
         ],
         formatter: function (x, data) { return data.formatted; }
     });

  });