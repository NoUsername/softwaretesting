<template>
	<div>
		<a href="#" id="link-view" @click="setMode('view')">Viewer</a>
		<a href="#" id="link-create" @click="setMode('create')">Creator</a>
	</div>
</template>

<script>
    export default {
        name: 'printer-menu',
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
    };
</script>