<template>
	<div class="modal__wrapper">
		<div class="modal__container">
			<slot name="header" />
			<slot name="body" />
			<slot name="footer" />
		</div>
	</div>
</template>

<style scoped>
.modal__wrapper {
	position: fixed;
	z-index: 9998;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, .5);
	display: flex;
	justify-content: center;
	align-items: center;
}
.modal__container {
	display: inline-block;
	padding: 16px 24px;
	background-color: #fff;
	border-radius: 6px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
}
</style>