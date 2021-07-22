import axios from '@/api'

const bringUserList = (getUid) => {
    return axios.get(`/chat/${getUid}`);
};

const bringUpMessageRecord = (getUid, selectedUser) => {
    return axios.get(`/recallChat/${getUid}/${selectedUser}` );
};

const saveReportInfo = (getUid, selectedUser, picked, reportDetail) => {
    
    return axios.post('/report', {
        reportId : getUid,
        reportedId : selectedUser,
        reportType : picked,
        reportDetail : reportDetail
    });
};

export default {
    bringUpMessageRecord,
    saveReportInfo,
    bringUserList,
};