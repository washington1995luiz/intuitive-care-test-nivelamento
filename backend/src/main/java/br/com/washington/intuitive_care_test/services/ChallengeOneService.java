package br.com.washington.intuitive_care_test.services;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;

public interface ChallengeOneService {

    String download(RequestFilesToScraping filesToScraping);
}
