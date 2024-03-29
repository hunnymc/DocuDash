import { defineStore } from 'pinia'
import axios from 'axios';
import Cookies from 'js-cookie';

export const useDocumentListStore = defineStore('documentList', {
    state: () => ({
        documents: [],
        selectedDocument: [],
        documentFilename: null,
        document_userId: null,
        callFunctionInComponentB: false,
        mainUrl: "https://capstone23.sit.kmutt.ac.th/kw2",
        // mainUrl: 'http://cp23kw2.sit.kmutt.ac.th:10003',
        // mainUrl: 'http://localhost:5002'
    }),
    persist: true,
    getters: {
        getDocuments() {
            return this.documents
        },
        getSelectedDocument() {
            return this.selectedDocument
        },
        getDocumentUserId() {
            return this.document_userId
        },
        getDocumentFilename() {
            return this.documentFilename
        },
    },
    actions: {
        setDocuments(documents) {
            this.documents = documents
        },
        setSelectedDocument(document) {
            this.selectedDocument = document
        },
        setCallFunctionInComponentB(value) {
            this.callFunctionInComponentB = value;
        },
        setDocumentUserId(userId) {
            this.document_userId = userId
        },
        setDocumentFilename(filename) {
            this.documentFilename = filename
        },
        async getdocumentFilenameAndUserIdFromAxios(documentId) {
            let mainUrl = "https://capstone23.sit.kmutt.ac.th/kw2";
            await axios.get(mainUrl + `/api/doc/${documentId}`,{
                headers: {
                    "Authorization": "Bearer " + Cookies.get("accessToken"),
                },
            })
                .then(response => {
                    this.documentFilename = response.data.filePath
                    this.document_userId = response.data.usersUserid.id
                    sessionStorage.setItem('documentFilename', this.documentFilename)
                    sessionStorage.setItem('document_userId', this.document_userId)
                    // console.log("store response.data:", response.data)
                    // console.log('store respoone userId id:', response.data.usersUserid.id)
                    // console.log('store documentFilename:', this.documentFilename)
                    // console.log('store document_userId:', this.document_userId)
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
})