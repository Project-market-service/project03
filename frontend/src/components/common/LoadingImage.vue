<template>
    <div class="loading" v-bind:style="style">
		<img src="../../assets/image/loading.gif" alt="">
	</div>
</template>

<script>
export default {
	props: ['size'],
	computed: {
		style() {
			const size = this.size;
			if (size === undefined) {
                return {
                    width: '100%',
                    height: '100%',
                };
            } else {
                return {
					width: `${size}px`,
					height: `${size}px`,
				};
            }
		},
	}
}
</script>

<style scoped>
.loading img {
    width: 100%;
	height: 100%;
	object-fit: contain;
}
</style>