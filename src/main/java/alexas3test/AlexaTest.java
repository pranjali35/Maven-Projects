package alexas3test;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AlexaTest {
	private static final Logger log = LoggerFactory.getLogger(AlexaTest.class);

	AmazonS3 test = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).withPathStyleAccessEnabled(true).build();
	static String bucketName = "clsalexatest";
//	static Date dNow = new Date( );

	static Date dNow = new Date( );
	static SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//	static String key = ft.format(dNow).toString();
	static String key ="2017-06-18";
//	System.out.println("Key:"+key);

	private void createBucket()
	{  

		test.createBucket(bucketName);
		log.info("Bucket Created successfully!!");

	}

	private void listBuckets()
	{
		System.out.println("Listing buckets");
		for (Bucket bucket : test.listBuckets()) {
			//        	   ObjectMetadata objectMetadata = new ObjectMetadata();
			//        	   Date d = new Date();

			//        	   if (d.equals(objectMetadata.getLastModified().getDate()));

			System.out.println(" - " + bucket.getName());
			System.out.println(bucket.getCreationDate() + ", " + bucket.getName());
		}
	}

	private void listObjects()
	{
		final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
		ListObjectsV2Result result;
		do {               
			result = test.listObjectsV2(req);

			for (S3ObjectSummary objectSummary : 
				result.getObjectSummaries()) {
				System.out.println("Keys are ******* " + objectSummary.getKey() + "  " +
						"(size = " + objectSummary.getSize() + 
						")");
				String keyobtained = objectSummary.getKey();
//				Date d = new Date();
				System.out.println("Keys of the object are:" +keyobtained);
//				if(keyobtained.equals(LocalDateTime.now()))
//				{
//					System.out.println("hello");
//				}
			}
			//             System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
			//             req.setContinuationToken(result.getNextContinuationToken());
		} while(result.isTruncated() == true ); 



	}
	public static void main(String[] args) throws IOException
	{

		AlexaTest at = new AlexaTest();
//		at.createBucket();
//		at.listBuckets();
//		at.listObjects();
		at.createObject();
		at.downloadObject();
		at.getObjectUrl(bucketName, key);
	}     

	public void createObject()
	{


		// Request server-side encryption.

		PutObjectRequest putRequest = new PutObjectRequest(
				bucketName, key, new File("C:/D_Drive/CLS-Alexa Skill/src/lib/Daily_Stats_20170613-1.xml")).withCannedAcl(CannedAccessControlList.PublicRead);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);     
		putRequest.setMetadata(objectMetadata);
		PutObjectResult response = test.putObject(putRequest);
		System.out.println("Uploaded object encryption status is " + 
				response.getSSEAlgorithm());
	}

	public void getObjectUrl(String bucketName, String key)
	{
	
		URL fileDownloadUrl = test.getUrl(bucketName, key);
		System.out.println("URL is:" +fileDownloadUrl);

		//			AmazonS3Client s3 = new AmazonS3Client();
		//			URL url= s3.getUrl(bucketName, key);

		//			System.out.println("Url IS:"+url );

		//    	  test.getUrl("pcttestb8914c57-d3f1-4b03-bdd8-7c3b7a4b742a", s3RelativeFilePath).toExternalForm();
	}

	//      https://s3.amazonaws.com/pcttestb8914c57-d3f1-4b03-bdd8-7c3b7a4b742a/Hello.txt

	public void downloadObject() throws IOException
	{
//		https://s3.amazonaws.com/clsalexatest/2017-06-17
		S3Object object = test.getObject(new GetObjectRequest("clsalexatest", "2017-06-17"));
		System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
		displayTextInputStream(object.getObjectContent());

	}


	private static void displayTextInputStream(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (true) {
			String line = reader.readLine();
			if (line == null) break;

			System.out.println("    " + line);
		}
		System.out.println();
	}

}


