<template>
  <div class="item-list">
		<div class="item-list__header">
			<h2>Recently Updated</h2>
			<div class="item-list__icons">
				<span class="material-icons">format_list_bulleted</span>
				<span class="material-icons">grid_view</span>
			</div>
		</div>
		<div class="item-list__items">
			<item-summary v-for='(item, i) in items' :key='i' :info='item' class="item-list__item"/>
		</div>
	</div>
</template>

<script>
import ItemSummary from './ItemSummary.vue'

export default {
	components: {
		ItemSummary,
	},
	props: [
		'items'
	],
}
</script>

<style scoped>
.item-list {
	padding: 10px;
}
.item-list__header {
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-bottom: 18px;
}
.item-list__header h2 {
	font-weight: 700;
	font-size: 26px;
}
.item-list__icons span {
	cursor: pointer;
	font-size: 30px;
}
.item-list__items {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
}
</style>