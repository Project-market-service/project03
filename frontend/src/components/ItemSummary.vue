<template>
  <div class="item">
		<div class="item__image">
			<img :src="info.image" alt="">
		</div>
		<div>
			{{ info.name }}
		</div>
		<div>
			{{ info.cost }} 원
		</div>
  </div>
</template>

<script>
export default {
	props: [
		'info'
	],
}
</script>

<style scoped>
.item {
	width: 180px;
	margin-bottom: 20px;
}
.item__image {
	width: 100%;
}
.item__image img {
	width: 100%;
	object-fit: cover;
}
</style>