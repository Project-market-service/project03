<template>
	<div class="user-image" v-bind:style="getUserImageStyle">
		<img v-if="hasUid" v-bind:src="getUserImageUrl" alt="">	
		<img v-else src="../../assets/image/help.png" alt="">
	</div>
</template>

<script>
export default {
	props: ['uid', 'size'],
	computed: {
		hasUid() {
			return this.uid !== '' || this.uid === undefined;
		},
		getUserImageUrl() {
			return `http://localhost:8081/fleamarket/account/image/${this.uid}`;
		},
		getUserImageStyle() {
			const defaultSize = 46;

			let size = this.size;
			if (size === undefined) {
				size = defaultSize; 
			}
			return {
					width: `${size}px`,
					height: `${size}px`,
				};
		},
	}
}
</script>

<style>
.user-image {
	border-radius: 50%;
	overflow: hidden;
}
.user-image img {
	width: 100%;
	height: 100%;
	object-fit: contain;
}
</style>