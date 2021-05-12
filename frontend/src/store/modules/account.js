import axios from '@/api'

// 세션에 저장되어 있는 사용자 정보를 가지고 온다.
// 없다면 uid를 비워둔다.

const state = {
	uid: '',
	username: '',
	isAuth: false,
};

const getters = {
	getUid(state) {
		return state.uid;
	},
	getUsername(state) {
		return state.username;
	},
	isAuth(state) {
		return state.isAuth;
	}
}

const mutations = {
	setUserInfo(state, payload) {
		state.uid = payload.id;
		state.username = payload.username;
		state.isAuth = true;
	},
	removeUserInfo(state) {
		state.uid = '',
		state.username = '',
		state.isAuth = false;
	}
}

const actions = {
	postLogin(context, {username, password}) {
		return new Promise(function(resolve, reject) {
			axios.post("/login", {username, password})
			.then(function(response) {
				context.commit('setUserInfo', response.data);
				// 세션에 사용자 정보 저장하기
				resolve(response);
			})
			.catch(function(error) {
				console.log(error);
				reject(error);
			});
		});
	},	
	async postLogout(context) {
		// 로그아웃 요청후 csrf 토큰을 갱신한다.
		await axios.post('/logout')
			.then(function(response) {
				axios.defaults.headers['X-CSRF-TOKEN'] = response.data;
			});
		context.commit('removeUserInfo');
	},
}

export default {
	state,
	getters,
	mutations,
	actions,
}
