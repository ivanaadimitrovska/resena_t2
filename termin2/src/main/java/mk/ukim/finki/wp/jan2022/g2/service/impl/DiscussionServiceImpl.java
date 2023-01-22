package mk.ukim.finki.wp.jan2022.g2.service.impl;

import mk.ukim.finki.wp.jan2022.g2.model.Discussion;
import mk.ukim.finki.wp.jan2022.g2.model.DiscussionTag;
import mk.ukim.finki.wp.jan2022.g2.model.User;
import mk.ukim.finki.wp.jan2022.g2.model.exceptions.InvalidDiscussionIdException;
import mk.ukim.finki.wp.jan2022.g2.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.jan2022.g2.repository.DiscussionRepository;
import mk.ukim.finki.wp.jan2022.g2.repository.UserRepository;
import mk.ukim.finki.wp.jan2022.g2.service.DiscussionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    public DiscussionServiceImpl(DiscussionRepository discussionRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Discussion> listAll() {
        return discussionRepository.findAll();
    }

    @Override
    public Discussion findById(Long id) {
        return discussionRepository.findById(id).orElseThrow(InvalidDiscussionIdException::new);
    }

    @Override
    public Discussion create(String title, String description, DiscussionTag discussionTag, List<Long> participants, LocalDate dueDate) {
        List<User> users = userRepository.findAllById(participants);
        if (users.isEmpty()) {
            throw new InvalidUserIdException();
        }
        Discussion discussion = new Discussion(title, description, discussionTag, users, dueDate);
        return discussionRepository.save(discussion);
    }

    @Override
    public Discussion update(Long id, String title, String description, DiscussionTag discussionTag, List<Long> participants) {
        List<User> users = userRepository.findAllById(participants);
        if (users.isEmpty()) {
            throw new InvalidUserIdException();
        }
        Discussion discussion = discussionRepository.findById(id).orElseThrow(InvalidDiscussionIdException::new);
        discussion.setTitle(title);
        discussion.setDescription(description);
        discussion.setTag(discussionTag);
        discussion.setParticipants(users);
        return discussionRepository.save(discussion);
    }

    @Override
    public Discussion delete(Long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(InvalidDiscussionIdException::new);
        discussionRepository.deleteById(id);
        return discussion;
    }

    @Override
    public Discussion markPopular(Long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(InvalidDiscussionIdException::new);
        discussion.setPopular(true);
        return discussionRepository.save(discussion);
    }

    @Override
    public List<Discussion> filter(Long participantId, Integer daysUntilClosing) {
        if (participantId != null && daysUntilClosing != null) {
            return discussionRepository.findAllByParticipantsContainingAndDueDateBefore(
                    userRepository.findById(participantId).orElseThrow(InvalidUserIdException::new),
                    LocalDate.now().plusDays(daysUntilClosing)
            );
        } else if (participantId != null) {
            return discussionRepository.findAllByParticipantsContaining(userRepository.findById(participantId).orElseThrow(InvalidUserIdException::new));
        } else if (daysUntilClosing != null) {
            return discussionRepository.findAllByDueDateBefore(LocalDate.now().plusDays(daysUntilClosing));
        } else {
            return discussionRepository.findAll();
        }
    }
}

//    отребно е да овозможите пребарување на дискусии според participant и преостанати денови
//- (да ги врати сите дискусии кои ќе се затворат пред да помине тој број на денови)