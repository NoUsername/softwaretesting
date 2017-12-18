import Vue from 'vue';

let utils = {};

// eventBus
utils.bus = new Vue();

utils.loadData = (minSpeed) => {
    var options = {};
    var minSpeed = minSpeed || 0;
    return fetch('/printers/' + minSpeed, options).then((response) => {
        return response.json();
    }).then((printers) => {
        utils.bus.$emit('printers', printers);
        return printers;
    });
};

utils.createPrinter = (name) => {
    var options = {
        method: 'POST'
    };
    return fetch('/newprinter/' + encodeURIComponent(name), options)
        .then((response) => {
            utils.bus.$emit('showMessage', '\'' + name + '\' saved');
            return 'ok';
        });
};

export default utils;