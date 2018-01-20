var h = 0;
var cap = 57 * 100 + 100000;

for (var b= cap; b <= cap + 17000; b = b + 17){
  var f = false;
  for (var d = 2; d * d <b; d++){
    if (b % d == 0) {
      f = true;
      break;
    }
  }
    
  if (f) h = h + 1;
  console.log("checking " + b);
}

console.log(h);
