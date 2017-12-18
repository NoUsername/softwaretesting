<template>
	<div>
		<p>Create new printer:</p>
		<label>Name:<input type="text" v-on:keyup.13="save" placeholder="3dMaster9000" name="printerName" v-model="name"/></label>

		<button @click="save">Save</button>
	</div>
</template>

<script>
    import utils from './utils.js';

    export default {
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
                this.$emit('setMessage', 'saving...');
                utils.createPrinter(this.name).then(function () {
                    component.name = '';
                });

            }
        }
    };
</script>