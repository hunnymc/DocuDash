import { defineStore } from 'pinia'


export const useDocumentListStore = defineStore('documentList', {
    state: () => ({
        documents: [],
        selectedDocument: [],
    }),
    getters: {
        getDocuments() {
            return this.documents
        },
        getSelectedDocument() {
            return this.selectedDocument
        }
    },
    actions: {
        setDocuments(documents) {
            this.documents = documents
        },
        setSelectedDocument(document) {
            this.selectedDocument = document
        }
    }
})
