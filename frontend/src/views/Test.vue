<template>
  <div>
    <UserImage v-if="!isUploading" :uid="getUid" :size="size" />
    <LoadingImage v-else :size="size" />
    <button @click="$refs.file.click()" class="user-image__edit-btn">
      Edit
      <input type="file" ref="file" @change="loadImage($event)" accept="image/*">
    </button>
    <button @click="deleteImage">
      Delete
    </button>
    <ImageCropModal v-if="showModal">
      <div slot="header" class="modal__header">
        <h2>Crop your profile picture</h2>
        <span @click="closeModal" class="material-icons modal__close-btn">close</span>
      </div>
      <div slot="body" class="modal__body">
        <Cropper
          :src="image.src"
          :stencil-component="$options.components.CircleStencil"
          :stencil-props="{
            aspectRatio: 1/1
          }"
          @change="change"
          class="cropper"
        ></Cropper>
      </div>
      <div slot="footer" class="modal__footer">
        <button @click="uploadImage">Upload Image</button>
      </div>
    </ImageCropModal>
  </div>
</template>

<script>
/* eslint-disable*/
import router from '@/router'
import { mapGetters } from 'vuex';
import { Cropper, CircleStencil } from 'vue-advanced-cropper'
import 'vue-advanced-cropper/dist/style.css';
import UserImage from '@/components/common/UserImage.vue';
import ImageCropModal from '@/components/common/CommonModal.vue';
import axios from '@/api';
import LoadingImage from '@/components/common/LoadingImage.vue';

function getMimeType(file, fallback = null) {
	const byteArray = (new Uint8Array(file)).subarray(0, 4);
    let header = '';
    for (let i = 0; i < byteArray.length; i++) {
       header += byteArray[i].toString(16);
    }
	switch (header) {
        case "89504e47":
            return "image/png";
        case "47494638":
            return "image/gif";
        case "ffd8ffe0":
        case "ffd8ffe1":
        case "ffd8ffe2":
        case "ffd8ffe3":
        case "ffd8ffe8":
            return "image/jpeg";
        default:
            return fallback;
    }
}

export default {
  components: {
    Cropper,
    CircleStencil,
    UserImage,
    ImageCropModal,
    LoadingImage
  },
  data: () => {
    return {
      size: 200,
      showModal: false,
      image: {
        src: null,
        type: null,
        file: null,
        coordinates: null,
      },
      isUploading: false,
    }
  },
  computed: {
    ...mapGetters(['getUid']),
    
  },
  methods: {
    change({coordinates, canvas}) {
      this.image.coordinates = coordinates;
    },
    loadImage(event) {
      const { files } = event.target;
      
      if (files && files[0]) {
        if (this.image.src) {
          URL.revokeObjectURL(this.image.src);
        }

        const reader = new FileReader();
        const blob = URL.createObjectURL(files[0]);
        
        reader.onload = (e) => {
          this.image = {
            src: blob,
            type: getMimeType(e.target.result, files[0].type),
            file: files[0],
          };
          const type = this.image.type;
          if (type !== 'image/png' && type !== 'image/jpeg') {
            alert('not image file!');
            this.closeModal();
          } else {
            this.showModal = true;
          }
        };

        reader.readAsArrayBuffer(files[0]);
      }
    },
    uploadImage() {
      this.isUploading = true;
      
      const form = new FormData();
      form.append('file', this.image.file);

      const coordinates = this.image.coordinates;
      Object.keys(coordinates).forEach(key => form.append(key, coordinates[key]));
      
      axios.post('/account/image', form, {
        header: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(response => {
        if (response.status === 200) {
          alert('File upload Success!');
        }
      })
      .catch(error => {
        alert(error.response.data);
      })
      .finally(() => {
        this.isUploading = false;
      });

      this.closeModal();
    },
    closeModal() {
      this.showModal = false;
      this.reset();
      this.destroy();
    },
    reset() {
      this.image = {
        src: null,
        type: null,
        file: null,
        coordinates: null,
      }
    },
    destroy() {
      if (this.image.src) {
        URL.revokeObjectURL(this.image.src);
      }
    },
    deleteImage() {
      this.isUploading = true;
      
      let msg;
      axios.delete('/account/image')
        .then(response => {
          if (response.status === 200) {
            msg = response.data;
          }
        })
        .catch(error => {
          alert(error.response.data);
        })
        .finally(() => {
          this.isUploading = false;
          alert(msg);
        });
    },
  }
}
</script>

<style scoped>
.user-image__edit-btn input {
  display: none;
}
.cropper {
  max-width: 70vw;
  max-height: 70vh;
  background: #DDD;
}
.modal__header {
  display: flex;
  justify-content: space-between;
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 10px;
}
.modal__header span {
  cursor: pointer;
  margin-left: 10px;
}
.modal__footer button {
  width: 100%;
  margin-top: 10px;
  padding: 6px 2px;
}
.modal__close-btn:hover {
  color: red;
}
</style>