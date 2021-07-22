<template>
<div class="container clearfix">
    
    <LeftSide></LeftSide>

    <RightSide></RightSide>
    
</div> <!-- end container -->
</template>

<style src="../assets/css/chat.css"></style>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { mapGetters } from 'vuex';
import LeftSide from '@/components/common/LeftSide.vue';
import RightSide from '@/components/common/RightSide.vue';
import {eventBus} from '@/main.js';

let socket = new SockJS('http://localhost:8081/fleamarket/ws');
let stompClient = Stomp.over(socket);
export default {
    name: 'App',
    
    components: {
        LeftSide,
        RightSide
        
    },

    computed: {
        ...mapGetters(['getUid']),
        
    },

    data: function() {
        return {

        url: 'http://localhost:8081/fleamarket',
        data : null,
                
        }
    },

    methods: {
            
            connectToChat(userId) {

                console.log("connecting to chat..." + userId);
                console.log("URL : " + this.url + '/ws');
                               
                stompClient.connect({}, (frame) => {

                    console.log("connected to: " + frame);

                    stompClient.subscribe("/topic/messages/" + userId, (response) => {

                        let data = JSON.parse(response.body);

                        // senderId / receiverId / getUid/ selectedUser
                        //if(data.receiverId )

                        data.createDate = new Date(data.createDate).toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");

                        console.log(data);

                        eventBus.messageIsReceived(data)

                        this.scrollToBottom();
                               
                    });
                });
            },

            //Vue 방식으로 바꿀수있으면 바꿔야함
            scrollToBottom() {

                window.$chatHistory = window.$('.chat-history');
                window.$chatHistory.scrollTop(window.$chatHistory[0].scrollHeight);
                
            },
                    
        },        

        created(){
            this.connectToChat(this.getUid);
        },    
}
</script>