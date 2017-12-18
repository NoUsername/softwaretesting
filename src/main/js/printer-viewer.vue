<template>
	<div>
		<p>Printers:</p>
		minimum Speed: <input v-on:input="debounceReload" name="minSpeed"
							  v-model="minSpeed" type="text"/>
		<span v-show="dirty" id="dirty-indicator">...</span>
		<ul id="printer-list">
			<li v-for="printer in printers" v-bind:title="printer.jsonDoc">
				{{ printer.name }}
			</li>
		</ul>
	</div>
</template>

<script>
    import utils from './utils.js';
    import {debounce} from 'lodash';

    export default {
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
                    this.debouncedReloadInternal || debounce(function (e) {
                        this.reload();
                    }, 500);
                this.dirty = true;
                this.debouncedReloadInternal();
            },
            reload: function () {
                if (this.minSpeed !== "" && isNaN(parseInt(this.minSpeed))) {
                    console.log('invalid minSpeed');
                    return;
                }
                var that = this;
                utils.loadData(parseInt(this.minSpeed) || 0)
                    .then(function () {
                        that.dirty = false;
                    });
            }
        }
    };
</script>