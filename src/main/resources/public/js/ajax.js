jQuery.ajax({
    type: 'POST',
    url: '/api/route',
    data: {start: 15000, dest: 1},
    async: false,
    success: function (d) {
        console.log(d);
    }
});