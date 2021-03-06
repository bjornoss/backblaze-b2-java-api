package synapticloop.b2.request;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2HideFileResponse;
import synapticloop.b2.response.B2ListFilesResponse;


public class B2HideFileRequestTest {

	private B2BucketResponse randomPrivateBucket = null;
	private B2FileResponse b2FileResponse = null;
	private B2HideFileResponse b2HideFileResponse = null;

	@Before
	public void setup() {
	}

	@Test
	public void testHideFile() throws B2ApiException {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		String bucketId = randomPrivateBucket.getBucketId();
		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(bucketId);

		B2ListFilesResponse b2ListFilesResponse = new B2ListFileNamesRequest(B2TestHelper.getB2AuthorizeAccountResponse(), bucketId).getResponse();
		assertEquals(1, b2ListFilesResponse.getFiles().size());

		b2HideFileResponse  = new B2HideFileRequest(B2TestHelper.getB2AuthorizeAccountResponse(), bucketId, b2FileResponse.getFileName()).getResponse();
		assertEquals("hide", b2HideFileResponse.getAction().toString());

		// we now have two versions...
		b2ListFilesResponse = new B2ListFileNamesRequest(B2TestHelper.getB2AuthorizeAccountResponse(), bucketId).getResponse();
		assertEquals(2, b2ListFilesResponse.getFiles().size());

		assertNull(null);
	}

	@After
	public void tearDown() throws B2ApiException {
		B2TestHelper.deleteFile(b2HideFileResponse.getFileName(), b2HideFileResponse.getFileId());
		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}
}
