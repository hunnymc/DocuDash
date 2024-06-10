import {defineStore} from 'pinia'
import axios from 'axios';
import Cookies from 'js-cookie';

export const useDocumentListStore = defineStore('documentList', {
    state: () => ({
        documents: [],
        selectedDocument: [],
        documentFilename: null,
        document_userId: null,
        callFunctionInComponentB: false,
        callFunctionToFetchDashboard: false,
        editDocument: {}
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
        getEditDocument() {
            return this.editDocument
        }
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
        setCallFunctionToFetchDashboard(value) {
            this.callFunctionToFetchDashboard = value;
        },
        setDocumentUserId(userId) {
            this.document_userId = userId
        },
        setDocumentFilename(filename) {
            this.documentFilename = filename
        },
        setEditDocument(document) {
            this.editDocument = document;
        },
        async getdocumentFilenameAndUserIdFromAxios(documentId) {
            let mainUrl = import.meta.env.VITE_API_URL;
            await axios.get(mainUrl + `/api/doc/${documentId}`, {
                headers: {
                    "Authorization": "Bearer " + Cookies.get("accessToken"),
                },
            })
                .then(response => {
                    this.documentFilename = response.data.filePath
                    this.document_userId = response.data.usersUserid.id
                    sessionStorage.setItem('documentFilename', this.documentFilename)
                    sessionStorage.setItem('document_userId', this.document_userId)
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
})