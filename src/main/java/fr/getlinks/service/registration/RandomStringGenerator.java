package fr.getlinks.service.registration;

public interface RandomStringGenerator
{
	String generateRandomString();

	String generateRandomString(int length);

	String generateRandomTimestampString();

	String generateRandomTimestampString(int randomSuffixLength);
}
