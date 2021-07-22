<template>
    <div class="chat">
        <div class="chat-header clearfix">
            <img alt="avatar" height="55px"
                 src="https://source.unsplash.com/featured/face=3" width="55px"/>

            <div class="chat-about">
                <div class="chat-with" id="selectedUserId" v-text="selectedOpnt">{{selectedOpnt}}</div>
                <button v-on:click="openModal">Report</button>

                <MyModal v-if="showModal">
                    <h1 slot="header" class="modal__header">Report Page</h1>
                    <hr>
                    <div slot="body" class="modal__body">
                        <div class="report-page">
                            <form v-on:submit="submitForm">
                                <p v-if="errors.length">
                                    <b>Please correct the following error(s):</b>
                                    <ul>
                                        <li v-for="error in errors" :key="error"> {{ error }}</li>
                                    </ul>
                                </p>

                                <div>
                                    <label for="name">My Account : {{this.getUid}}</label> 
                                </div>
                                <div>
                                    <label> {{this.reported}}</label>
                                </div>
                                <br>
                               
                                <div id='list-of-issue'>
                                    <h1 slot="header" class="modal__sub_header">신고하는 이유를 선택해주세요</h1>
                                    <hr>
                                    <br>
                                    <div>
                                        <label for="issue1">판매금지 품목이에요</label>
                                        <input type="radio" id="issue1" value="issue1" v-model="picked">
                                    </div>
                                    <div>
                                        <label for="issue2">중고거래 게시글이 아니에요</label>
                                        <input type="radio" id="issue2" value="issue2" v-model="picked">
                                    </div>
                                    <div>
                                        <label for="issue3">전문 판매업자 같아요</label>
                                        <input type="radio" id="issue3" value="issue3" v-model="picked">
                                    </div>
                                    <div>
                                        <label for="issue4">카카오톡/안전결제 등으로 유도하는 사기글이에요</label>
                                        <input type="radio" id="issue4" value="issue4" v-model="picked">
                                    </div>
                                    <div>
                                        <label for="issue5">기타 사유 선택</label>
                                        <input type="radio" id="issue5" value="issue5" v-model="picked">
                                    </div>
                                    <br>
                                    <span>체크한 항목: {{ picked }}</span>
                                </div>
                                <div>
                                    <span>자세한 사항</span>
                                </div>
                                <div>
                                    <textarea v-model="reportDetail" placeholder="여러줄을 입력해보세요"></textarea>
                                </div>

                                
                                <button type="submit">Submit</button>

                                <!-- <p>
                                    <input type="submit" value="Submit">
                                </p> -->
                            </form>
                        </div>
                    </div>
                    <span slot="footer">
                        
                        <button v-on:click="closeModal">Close</button>
                    </span>
                </MyModal>

                <div class="chat-num-messages"></div>
            </div>
            <i class="fa fa-star"></i>
        </div> <!-- end chat-header -->

        <div class="chat-history">
            <ul>                	
                 <li class="clearfix" id="chat-contents" v-for="(content, index) in array" :key="index">
                    
                    <div v-if="getUid != content.senderId">
                        <div class="message-data">
                        <span class="message-data-name"><i class="fa fa-circle online"></i> {{content.senderId}}</span>
                        <span class="message-data-time">{{content.createDate}}, Today</span>
                        </div>
                        <div class="message my-message">
                            {{content.content}}
                        </div>
                    </div>

                    <div v-else>
                        <div class="message-data align-right">
                        <span class="message-data-time">{{content.createDate}}, Today</span> &nbsp; &nbsp;
                        <span class="message-data-name">You</span> <i class="fa fa-circle me"></i>
                        </div>
                        <div class="message other-message float-right">
                            {{content.content}}
                        </div>
                    </div>

                    
                </li>
                
            </ul>

        </div> <!-- end chat-history -->

        <div class="chat-message clearfix">
            <textarea id="message-to-send" name="message-to-send" placeholder="Type your message" rows="3" v-model="message"></textarea>

            <i class="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
            <i class="fa fa-file-image-o"></i>
            
            <button id="sendBtn" @click="sendMessage(message)">Send</button>

            
        </div> <!-- end chat-message -->

    </div> <!-- end chat -->
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { mapGetters } from 'vuex';
import MyModal from '@/components/common/CommonModal.vue';
import {eventBus} from '@/main.js';
import chatApi from '@/api/chat.js';

let socket = new SockJS('http://localhost:8081/fleamarket/ws');
let stompClient = Stomp.over(socket);
export default {
    components: {
        MyModal,
    },

    computed: {
        ...mapGetters(['getUid']),
        
    },

    data: function() {

        return {

        userData : [       ],
        url: 'http://localhost:8081/fleamarket',
        data : null,
        selectedUser : '',
        opponent : '',
        reported : '',
        message : '',       
        array : [],
        time : null,
        selectedOpnt : '',
        showModal: false,   // 모달의 상태를 관리하는 변수
        errors: [],
        picked : '',
        reportDetail: '',

        }
    },

    methods: {
            
        openModal() {   // 모달의 상태 변수를 `true`로 변경한다.
        this.showModal = true;
        },
        closeModal() {  // 모달의 상태 변수를 `false`로 변경한다.
        this.showModal = false;
        },

        checkForm: function (e) {
            if (this.name && this.age) {
                return true;
            }

            this.errors = [];

            if (!this.name) {
                this.errors.push('Name required.');
            }
            if (!this.age) {
                this.errors.push('Age required.');
            }

            e.preventDefault();
        },

        submitForm(){
            
            chatApi.saveReportInfo(this.getUid, this.selectedUser, this.picked, this.reportDetail)
            .then(res => {
                console.log(res);
                if(res.status === 200){
                    console.log('your report is successfully submitted');
                }
            }).catch(err => {
                console.log(err);
            })
        },
        
        sendMsg(from, text) {

            if(this.selectedUser == null || this.selectedUser.length == 0){
                document.getElementById("sendBtn").disabled = true;
                alert("채팅 상대를 선택해주세요.")
            }
            else{
            
                stompClient.send(`/app/chat/${from}/${this.selectedUser}`, {}, JSON.stringify({
                senderId: from,
                receiverId: this.selectedUser,
                content: text,
                                    
            }));
            }

            
        },

        //Vue 방식으로 바꿀수있으면 바꿔야함
        scrollToBottom() {

            window.$chatHistory = window.$('.chat-history');
            window.$chatHistory.scrollTop(window.$chatHistory[0].scrollHeight);
            
        },
        
        sendMessage(message) {

            this.sendMsg(this.getUid, message);
            document.getElementById("sendBtn").disabled = false;
            this.scrollToBottom();    
            this.scrollToBottom();
            this.message = '';
                
            }
        },
        
                    
        created(){
            
            eventBus.$on('userIsSelected' ,(user) => {
                this.selectedUser = user
            })

            eventBus.$on('opntIsSelected', (opnt) =>{
                this.selectedOpnt = opnt
            })

            eventBus.$on('reportIsSelected', (report)=>{
                this.reported = report
            })

            eventBus.$on('messageIsReceived', (message)=>{
                this.array.push(message)
            })
        
        },

}

</script>

<style>
.modal__header {
	font-weight: 700;
	font-size: 24px;
}

.modal__sub_header {
    font-weight: 500;
    font-size: 18px;
}

.modal__body {
	width: 500px;
    height: 700px;
    margin-top: 30px;
}
</style>