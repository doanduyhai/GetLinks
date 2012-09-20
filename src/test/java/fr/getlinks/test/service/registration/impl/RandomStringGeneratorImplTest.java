package fr.getlinks.test.service.registration.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import fr.getlinks.service.registration.impl.RandomStringGeneratorImpl;

@Test(groups = "randomGeneratorTestGroup")
public class RandomStringGeneratorImplTest
{

	private RandomStringGeneratorImpl randomStringGenerator = new RandomStringGeneratorImpl();

	@Test
	public void testRandomString_SERVICE()
	{
		String randomString = this.randomStringGenerator.generateRandomString();
		assertTrue(StringUtils.isNotBlank(randomString));
		assertEquals(randomString.length(), 32, "randomString is 32 characters long");
	}

	@Test
	public void testRandomTimestampString_SERVICE()
	{
		String randomTimestampString = this.randomStringGenerator.generateRandomTimestampString();
		assertTrue(StringUtils.isNotBlank(randomTimestampString));
		assertEquals(randomTimestampString.length(), 16, "randomTimestampString is 16 characters long");
	}

	@Test
	public void testCompareRandomTimestampString_SERVICE() throws InterruptedException
	{
		String randomTimestampString1 = this.randomStringGenerator.generateRandomTimestampString();
		assertTrue(StringUtils.isNotBlank(randomTimestampString1));

		Thread.sleep(10);
		String randomTimestampString2 = this.randomStringGenerator.generateRandomTimestampString();
		assertTrue(StringUtils.isNotBlank(randomTimestampString2));

		assertTrue(randomTimestampString1.compareTo(randomTimestampString2) < 0, "randomTimestampString1 should be before randomTimestampString2");
	}

	@Test
	public void testRandomTimestampStringWithRandomSuffix_SERVICE()
	{
		String randomTimestampString = this.randomStringGenerator.generateRandomTimestampString(16);
		assertTrue(StringUtils.isNotBlank(randomTimestampString), "randomTimestampString should not be blank");
		assertEquals(randomTimestampString.length(), 32, "randomTimestampString is 32 characters long");
	}
}
