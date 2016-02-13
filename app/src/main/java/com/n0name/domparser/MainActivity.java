package com.n0name.domparser;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //final TextView textView = new TextView(this);
        final ListView listView = new ListView(this);


        //new ConnectTask(). execute("http://abelski.com/image.png");


         new AsyncTask<String, Integer, List>() {

                InputStream is = null;
                HttpURLConnection con = null;
                //StringBuffer stringBuffer = new StringBuffer();
                List<String> stringList = new LinkedList<String>();


             @Override
                protected List doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        //  URL url = (URL) params[0];
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.connect();
                        is = con.getInputStream();
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(is);
                        NodeList nameList = doc.getElementsByTagName("NAME");
                        NodeList rateList = doc.getElementsByTagName("RATE");
                        NodeList changeList = doc.getElementsByTagName("CHANGE");
                        NodeList countryList = doc.getElementsByTagName("COUNTRY");

                        int length = nameList.getLength();

                        for (int i = 0; i < length; i++) {
                           // stringBuffer.append(nameList.item(i).getFirstChild().getNodeValue());
                            stringList.add(nameList.item(i).getFirstChild().getNodeValue());
                           // stringBuffer.append(" "+countryList.item(i).getFirstChild().getNodeValue());
                            stringList.add(" "+countryList.item(i).getFirstChild().getNodeValue());
                            // stringBuffer.append(": " + rateList.item(i).getFirstChild().getNodeValue());
                            stringList.add(": " + rateList.item(i).getFirstChild().getNodeValue());
                           // stringBuffer.append(" (" + changeList.item(i).getFirstChild().getNodeValue() + ")\n\n");
                            stringList.add(" (" + changeList.item(i).getFirstChild().getNodeValue() + ")\n\n");

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (con != null) {
                            con.disconnect();
                        }
                        return stringList;

                    }


                }

                @Override
                protected void onPostExecute(List stringList) {
                    super.onPostExecute(stringList);
                   // textView.setText(stringBuffer);
                    ListAdapter adapter = new ArrayAdapter<String>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            stringList);
                    listView.setAdapter(adapter);
                    setContentView(listView);

                }
         }.execute("http://www.boi.org.il/currency.xml");
           // connector.execute("http://www.boi.org.il/currency.xml");







    }

}
