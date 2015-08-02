$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2013 Q1',
            Albert: 2666,
            Counter: null,
            App: 2647
        }, {
            period: '2013 Q2',
            Albert: 2778,
            Counter: 2294,
            App: 2441
        }, {
            period: '2013 Q3',
            Albert: 4912,
            Counter: 1969,
            App: 2501
        }, {
            period: '2013 Q4',
            Albert: 3767,
            Counter: 3597,
            App: 5689
        }, {
            period: '2014 Q1',
            Albert: 6810,
            Counter: 1914,
            App: 2293
        }, {
            period: '2014 Q2',
            Albert: 5670,
            Counter: 4293,
            App: 1881
        }, {
            period: '2014 Q3',
            Albert: 4820,
            Counter: 3795,
            App: 1588
        }, {
            period: '2014 Q4',
            Albert: 15073,
            Counter: 5967,
            App: 5175
        }, {
            period: '2015 Q1',
            Albert: 10687,
            Counter: 4460,
            App: 2028
        }, {
            period: '2015 Q2',
            Albert: 8432,
            Counter: 5713,
            App: 1791
        }],
        xkey: 'period',
        ykeys: ['Albert', 'Counter', 'App'],
        labels: ['Albert', 'Counter', 'App'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Popcorn Chicken Boxed Meal",
            value: 10
        }, {
            label: "Kentucky Burger Boxed Meal",
            value: 20
        }, {
            label: "Kentucky Burger",
            value: 30
        }, {
            label: "Kentucky Burger",
            value: 40
        }, {
            label: "Zinger Stacker Boxed meal",
            value: 50
        }, {
            label: "Zinger Burger",
            value: 60
        }, {
            label: "Zinger Stacker Burger",
            value: 70
        }, {
            label: "Mex Fresh Twister Combo",
            value: 100
        }, {
            label: "Spicy Hot Twister Combo",
            value: 200
        }, {
            label: "Chicken Salad Twister Combo",
            value: 500
        },
        
        ],
        resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2008',
            a: 100000,
            b: 900000
        }, {
            y: '2009',
            a: 150000,
            b: 750000
        }, {
            y: '2010',
            a: 210000,
            b: 700000
        }, {
            y: '2011',
            a: 290000,
            b: 650000
        }, {
            y: '2012',
            a: 350000,
            b: 500000
        }, {
            y: '2013',
            a: 750000,
            b: 400000
        }, {
            y: '2014',
            a: 700000,
            b: 200000
        }],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['EFTPOS', 'Cash'],
        hideHover: 'auto',
        resize: true
    });

});
