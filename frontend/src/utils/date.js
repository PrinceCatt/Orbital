export function getDate() {
    var date = new Date();
    var year = date.getFullYear();
    var dateArr = [
        date.getMonth() + 1, //months are 0-11
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
    ];
  
    for (var i = 0; i < dateArr.length; i++) {
        if (dateArr[i] >= 1 && dateArr[i] <= 9) {
            dateArr[i] = "0" + dateArr[i];
        }
    }
  
    var strDate =
        year +
        "-" +
        dateArr[0] +
        "-" +
        dateArr[1] +
        " " +
        dateArr[2] +
        ":" +
        dateArr[3] +
        ":" +
        dateArr[4];
    return strDate;
}