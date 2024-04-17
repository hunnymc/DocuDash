import {defineStore} from 'pinia'
import axios from "axios";
import moment from "moment";


export const useGraphStore = defineStore('graph', {
    state: () => ({
        graph: [],
        series_pie: null,
        waveGraph: null,
        series_wave: null,
        chartOptions_wave: null,
    }),
    persist: true,
    getters: {
        getGraphs() {
            return this.graph
        },
        getSeriesPie() {
            return this.series_pie
        },
        getSeriesWave() {
            return this.series_wave
        },
        getWaveGraph() {
            return this.waveGeaph
        },
        getChartOptionsWave() {
            return this.chartOptions_wave
        },
    },
    actions: {
        setGraphs(graph) {
            this.graph = graph
        },
        async getPieInfo() {
            let mainURL = import.meta.env.VITE_API_URL;
            await axios.get(mainURL + '/api/approve/graph/pie')
                .then((response) => {
                    this.series_pie = [
                        response.data.totalStatus2,
                        response.data.totalStatus3,
                        response.data.totalStatus4,
                        response.data.totalStatus5
                    ];
                    console.log("(Store) pie chart: ", this.series_pie);
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        async getWaveInfo() {
            let mainURL = import.meta.env.VITE_API_URL;
            await axios.get(mainURL + '/api/approve/graph/wave')
                .then((response) => {
                    this.waveGraph = [
                        response.data.newRequestsThisWeek,
                        response.data.newRequestsLastWeek,
                        response.data.percentageIncreaseFromLastWeek,
                        response.data.day1,
                        response.data.day2,
                        response.data.day3,
                        response.data.day4,
                        response.data.day5,
                        response.data.day6,
                        response.data.day7,
                        response.data.countOfDay1,
                        response.data.countOfDay2,
                        response.data.countOfDay3,
                        response.data.countOfDay4,
                        response.data.countOfDay5,
                        response.data.countOfDay6,
                        response.data.countOfDay7
                    ];

                    console.log("(Store) wave graph: ", this.waveGraph);

                    this.chartOptions_wave = {
                        chart: {
                            height: 350,
                            type: 'area',
                            zoom: {
                                enabled: false
                            }
                        },
                        dataLabels: {
                            enabled: false
                        },
                        stroke: {
                            curve: 'smooth'
                        },
                        xaxis: {
                            type: 'date',
                            categories: [
                                moment.unix(this.waveGraph[9] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[8] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[7] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[6] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[5] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[4] / 1000).format('DD/MM'),
                                moment.unix(this.waveGraph[3] / 1000).format('DD/MM')
                            ]
                        },
                    };
                    console.log("(Store) wave chart options: ", this.chartOptions_wave);

                    this.series_wave = [
                        {
                            name: 'จำนวนเอกสาร',
                            data: [
                                this.waveGraph[16],
                                this.waveGraph[15],
                                this.waveGraph[14],
                                this.waveGraph[13],
                                this.waveGraph[12],
                                this.waveGraph[11],
                                this.waveGraph[10]
                            ]
                        }
                    ]

                    console.log("(Store) wave chart: ", this.series_wave);
                })
                .catch((error) => {
                    console.log(error);
                });
        }

    }
})