<template>

    <div>
        <div>
            <h1 slot="header" class="modal__header">Upload Page</h1>

             <br> 

            <div>
                <div class="report-page">
                    <form v-on:submit="onUpload">

                        <h1>제목</h1>
                        <input v-model="postTitle" placeholder="여기를 수정해보세요">

                        <h1>부제목</h1>
                        <input v-model="postSubtitle" placeholder="여기를 수정해보세요">

                        <h1>내용</h1>            
                        <textarea v-model="content" placeholder="여러줄을 입력해보세요"></textarea>

                        <div id='list-of-issue'>

                            <h1>카테고리 선택</h1>

                            <div>
                                <label for="IT">IT</label>
                                <input type="radio" id="IT" value="IT" v-model="category">
                            
                                <label for="Design">디자인</label>
                                <input type="radio" id="Design" value="Design" v-model="category">
                            
                                <label for="Architect">건축</label>
                                <input type="radio" id="architect" value="architect" v-model="category">
                            
                                <label for="Marketing">마케팅</label>
                                <input type="radio" id="Marketing" value="Marketing" v-model="category">
                            
                                <label for="Accounting">회계</label>
                                <input type="radio" id="Accounting" value="Accounting" v-model="category">
                            </div>

                        </div>
                        
                        <br>           

                        <input type="file" @change="onFileSelected" ref="fileInput">
                        
                        <br>
                        <br>

                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>

            <br>

            <button @click="bringPostList">Bring Post List</button>
            <ul v-if="a">
                <li v-for="(item, index) in this.postList" :key="index">
                    
                    <a href="#" @click="bringImages(i.postNo)">
                        {{item.postNo}}
                    </a>
                    <ul>
                        <li v-for="i in item.numOfPage" :key="i">
                            <img :src="`http://localhost:8081/fleamarket/images/${getUid}/${item.postNo}/${i-1}`"/>
                        </li>
                    </ul>
                                            
                </li>
            </ul>
        </div>      
    </div>

</template>

<script>

import axios from '@/api'
import { mapGetters } from 'vuex';

var regex = new RegExp("pdf$", 'i');
var maxSize = 5242880; //5MB

export default{

    computed: {
        ...mapGetters(['getUid']),
        
    },

    data (){
        return{
            selectedFile: null,
            fileName: '',
            postTitle : "lion king",
            postSubtitle : "subtitle2",
            content : "so excited",
            category: '',
            postList: [     ],
            a: false,
            
        }
    },
    methods:{

        checkExtension(fileName, fileSize){
            if(fileSize >= maxSize){
                alert("파일 사이즈가 초과하였습니다.");
                return false;
            }

            if(regex.test(fileName) == 0){
                alert("pdf외에 파일은 업로드 할 수 없습니다.");
                return false;
            }

            return true;
        },

        onFileSelected(event){
            this.selectedFile = event.target.files[0]
        },

        onUpload(){

            const fd = new FormData();
            
            if(!this.checkExtension(this.selectedFile.name, this.selectedFile.size)){
                return false;
            }
            else{

            fd.append('postTitle', this.postTitle);

            fd.append('postSubtitle', this.postSubtitle);

            fd.append('content', this.content);       
            
            fd.append('category', this.category);

            fd.append('file', this.selectedFile, this.selectedFile.name);

            fd.append('userId', this.getUid);
        
            axios.post("/uploadFile", fd, {

                Headers: {
                    'Content-Type' : 'multipart/form-data'
                },
                               
                onUploadProgress : uploadEvent => {
                    console.log('Upload Progress: ' + Math.round(uploadEvent.loaded / uploadEvent.total * 100) + '%')
                }
                
            })
                .then(res =>{
                    console.log(JSON.stringify(res));
                })
            }
            
        },

        bringPostList(){

            const prefix = "http://localhost:8081/fleamarket/images/";

            this.a = true;

            axios.get(`/postList/${this.getUid}`)
            .then(response => {

                let pl = response.data;

                console.log(JSON.stringify(pl));

                for(var idx in pl){
                    console.log(pl[idx]);
                    console.log(pl[idx].numOfPage);
                }

                this.postList = pl;

            })
            .catch(function(error){
                console.log(error)
            });
        },

    }
}
</script>