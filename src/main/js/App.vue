<template>
	<div id="app">
		<transition name="slide-top">
			<notification v-show="showMessage">{{ message }}</notification>
		</transition>

		<printer-menu v-bind:initial-mode="mode"
					  @navigate="navigate">
		</printer-menu>

		<div class="content-container">
			<transition name="slide-fade">
				<printer-viewer v-show="mode == 'view'"
								v-bind:printers="printers">
				</printer-viewer>
			</transition>
			<transition name="slide-fade">
				<printer-creator v-show="mode == 'create'"
								 @setMessage="setMessage">
				</printer-creator>
			</transition>
		</div>
	</div>
</template>

<script>
    import utils from './utils.js';
    import PrinterMenu from './printer-menu.vue';
    import PrinterCreator from './printer-creator.vue';
    import PrinterViewer from './printer-viewer.vue';

    export default {
        name: 'app',
        components: {
            PrinterMenu,
            PrinterViewer,
            PrinterCreator
        },
        data: () => {
            console.log('app data created');
            return {
                showMessage: true,
                showMessageTimeout: 0,
                mode: 'view',
                printers: [],
				message: ''
            };
        },
        methods: {
            navigate: function (newMode) {
                this.mode = newMode;
            },
            loadData: () => {
                utils.loadData();
            },
            setMessage: function (message) {
                this.message = message;
            }
        },
        watch: {
            message: function (val) {
                var that = this;
                this.showMessage = true;
                clearTimeout(this.showMessageTimeout);
                this.showMessageTimeout = setTimeout(() => {
                    that.showMessage = false;
                }, 2500);
            }
        },
        mounted: function () {
            utils.bus.$on('showMessage', this.setMessage);
            utils.bus.$on('printers', (printers) => {
                this.printers = printers;
			});
            this.message = 'loading';
            this.loadData();
        }
    };
</script>