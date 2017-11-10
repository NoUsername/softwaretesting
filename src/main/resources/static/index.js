(function () {
    var data = {
        message: 'loading',
        mode: 'view',
        printers: [],
        showMessage: true,
        showMessageTimeout: 0
    };

    function loadData(minSpeed) {
        var options = {};
        var minSpeed = minSpeed || 0;
        return fetch('/printers/' + minSpeed, options).then(function (response) {
            return response.json();
        }).then(function (printers) {
            data.printers = printers;
            return printers;
        });
    }

    function createPrinter(name) {
        var options = {
            method: 'POST'
        };
        return fetch('/newprinter/' + encodeURIComponent(name), options)
            .then(function (response) {
                data.message = '\'' + name + '\' saved';
                return 'ok';
            });
    }

    Vue.component('printer-menu', {
        template: '#printer-menu-template',
        props: ['initialMode'],
        data: function () {
            return {
                mode: this.initialMode
            };
        },
        methods: {
            setMode: function (mode) {
                this.mode = mode;
                this.$emit('navigate', mode);
            }
        }
    });


    Vue.component('printer-viewer', {
        template: '#printer-viewer-template',
        props: ['printers'],
        data: function () {
            return {
                minSpeed: 0,
                dirty: false
            };
        },
        methods: {
            debounceReload: function () {
                this.debouncedReloadInternal =
                    this.debouncedReloadInternal || _.debounce(function (e) {
                        this.reload();
                    }, 500);
                this.dirty = true;
                this.debouncedReloadInternal();
            },
            reload: function () {
                var that = this;
                loadData(this.minSpeed).then(function () {
                    that.dirty = false;
                });
            }
        }
    });

    Vue.component('printer-creator', {
        template: '#printer-creator-template',
        data: function () {
            return {
                name: ''
            };
        },
        methods: {
            save: function () {
                if (!this.name) {
                    return;
                }
                var component = this;
                data.message = 'saving...';
                createPrinter(this.name).then(function () {
                    component.name = '';
                });

            }
        }
    });


    var vm = new Vue({
        el: '#app',
        data: data,
        methods: {
            navigate: function (newMode) {
                data.mode = newMode;
            }
        },
        watch: {
            message: function (val) {
                data.showMessage = true;
                console.log('enabling showMessage')
                clearTimeout(data.showMessageTimeout);
                data.showMessageTimeout = setTimeout(function () {
                    data.showMessage = false;
                }, 2500);
            }
        }
    });

    loadData().then(function () {
        data.message = 'loaded';
    });

    window.data = data;

})();