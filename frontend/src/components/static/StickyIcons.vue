<template>
  <div class="sticky-container">
		<ul class="icons">
			<li class="material-icons">add_circle</li>
			<li class="material-icons">question_answer</li>
		</ul>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.sticky-container {
	position: sticky;
	bottom: 0;
	text-align: right;
	width: 968px; 
	margin: 0 auto;
	background-color: transparent;
}
@media (max-width: 1024px) {
	.sticky-container {
		width: 100%;
	}
}
.icons {
	display: inline-block;
	padding: 10px;
}
.icons li {
	display: block;
	cursor: pointer;
	font-size: 32px;
	padding: 5px;
}
</style>