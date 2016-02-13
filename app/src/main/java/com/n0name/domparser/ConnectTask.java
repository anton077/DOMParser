package com.n0name.domparser;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by N0Name on 13-Feb-16.
 */
public class ConnectTask extends AsyncTask<String, Integer, StringBuffer> {
        InputStream is = null;
        HttpURLConnection con = null;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected StringBuffer doInBackground(String... params) {
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
                NodeList list = doc.getElementsByTagName("NAME");
                int length = list.getLength();

                for (int i = 0; i < length; i++) {
                    stringBuffer.append(list.item(i).getFirstChild().getNodeValue());

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
                        return stringBuffer;

                    }


                }

                @Override
                protected void onPostExecute(StringBuffer stringBuffer) {
                    super.onPostExecute(stringBuffer);
                }

}
