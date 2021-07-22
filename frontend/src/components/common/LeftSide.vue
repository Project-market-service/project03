<template>
    <div class="people-list" id="people-list">
        <div class="search">
            <label for="me">My Account : {{this.getUid}}</label>
        </div>
        <ul class="list" id="usersList" >
            <a href="#" class="people-list-inside" @click="selectUser(i.user, i.name)" :key="i.user" :value="i" v-for="i in userData">
                <li class="clearfix" >
                    <UserImage v-bind:uid="i.user"></UserImage>
                    <div class="about">
                        <span id="userNameAppender" class="name">{{i.name}}</span>
                        <span>{{date}}</span>
                        <div>{{i.content}}</div>
                        
                        <div class="status">
                            <i class="fa fa-circle offline"></i>
                        </div>
                    </div>
                </li>
            </a>
        </ul>
    </div>
</template>
<script>
import { mapGetters } from 'vuex';
import UserImage from '@/components/common/UserImage.vue';
import {eventBus}  from '@/main.js';
import chatApi from '@/api/chat.js';

export default {
    
    components: {
        UserImage,     
    },

    computed: {
        ...mapGetters(['getUid']),
    },

    data: function() {
        return {
            userData : [       ],
            selectedUser : '',
            opponent : '',
            reported : '',
            date : '',
        }
    },
    methods: {
        
            bringUserList(getUid){

                chatApi.bringUserList(getUid)
              
                .then(response => {
                    
                    let userList = response.data;

                    this.userData = userList;

                    for(var idx in userList){

                        this.date = new Date(userList[idx].createDate).toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");               
                    }

                })
                .catch(function(error){
                    console.log(error);
                }); 
            },

            recallChat(getUid,selectedUser) {
               
                chatApi.bringUpMessageRecord(getUid, selectedUser)

                .then(response => {
                    
                   let messages = response;
                        
                    console.log(messages);
                })
                .catch(function(error){
                    console.log(error);
                })  
            },

            selectUser(userId, userName) {

                console.log("selecting users: " + userId);
                this.selectedUser = userId;

                console.log(userName);
                
                this.recallChat(this.getUid, this.selectedUser);
                            
                this.opponent ='Chat with ' + userName;
                this.reported ='Would you like to report ' + userName +'?';
        
                eventBus.userIsSelected(this.selectedUser)

                eventBus.opntIsSelected(this.opponent)

                eventBus.reportIsSelected(this.reported)
                
            },


    },

    created: function(){
            
            this.bringUserList(this.getUid);
        },
}
</script>

<style type="text/css">
 .people-list-inside:link { color: white; text-decoration: none;}
 .people-list-inside:visited { color: white; text-decoration: none;}
 .people-list-inside:hover { color: white; text-decoration: none;}

span{
    margin-right: 10px;
}
</style>



