package fr.getlinks.service.registration.impl;

import java.util.StringTokenizer;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;

import org.apache.commons.lang.RandomStringUtils;

import fr.getlinks.service.registration.RandomStringGenerator;

public class RandomStringGeneratorImpl implements RandomStringGenerator
{
	private static final int DEFAULT_RANDOM_LENGTH = 32;

	@Override
	public String generateRandomString()
	{
		return randomString(DEFAULT_RANDOM_LENGTH);
	}

	@Override
	public String generateRandomString(int length)
	{
		return randomString(length);
	}

	@Override
	public String generateRandomTimestampString()
	{
		return reorderTimeUUId(TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString(), 0);
	}

	@Override
	public String generateRandomTimestampString(int randomSuffixLength)
	{
		return reorderTimeUUId(TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString(), randomSuffixLength);
	}

	private String reorderTimeUUId(String originalTimeUUID, int randomStringLength)
	{
		StringTokenizer tokens = new StringTokenizer(originalTimeUUID, "-");
		if (tokens.countTokens() == 5)
		{
			String time_low = tokens.nextToken();
			String time_mid = tokens.nextToken();
			String time_high_and_version = tokens.nextToken();
			String randomSuffix = "";
			if (randomStringLength > 0)
			{
				randomSuffix = randomString(randomStringLength);
			}
			return time_high_and_version + time_mid + time_low + randomSuffix;
		}

		return originalTimeUUID;
	}

	private String randomString(int length)
	{
		return RandomStringUtils.randomAlphanumeric(length);
	}

}
