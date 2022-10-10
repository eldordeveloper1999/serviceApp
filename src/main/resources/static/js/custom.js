$('#dropdownYear').each(function() {

    var year = (new Date()).getFullYear();
    var current = year;
    year -= 3;
    for (var i = 0; i < 6; i++) {
        if ((year+i) == current)
            $(this).append('<option selected value="' + (year + i) + '">' + (year + i) + '</option>');
        else
            $(this).append('<option value="' + (year + i) + '">' + (year + i) + '</option>');
    }

})