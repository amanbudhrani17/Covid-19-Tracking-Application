package com.covidtracker.covidtracker.services;
import com.covidtracker.covidtracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
@Service
public class CoronaVirusDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/amanbudhrani17/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats = new ArrayList<>();
    public List<LocationStats> getAllStats(){
        return allStats;
    }
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for(CSVRecord record:records){
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get(("Country/Region")));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDiffFromPrevDay(latestCases-prevDayCases);
            newStats.add(locationStats);
//            System.out.println(locationStats);
        }
        Queue<LocationStats> a = new LinkedList<>();
        Queue<LocationStats> b = new LinkedList<>();
        for(LocationStats l : newStats){
            if(l.getState()==""){
                a.add(l);
            }
            else{
                b.add(l);
            }
        }
        newStats = new ArrayList<>();
        while(!b.isEmpty()){
            newStats.add(b.remove());
        }
        while (!a.isEmpty()){
            newStats.add(a.remove());
        }

        this.allStats =newStats;
    }
}
//csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv