from github import Github
import re

def is_pull(issue):
    if "pull/" + str(issue.number) in issue.html_url:
        return True
    else:
        return False
    
def recognize_issue_numbers(pull):
    issue_numbers = re.findall(r"#(\d+)", f"{pull.title} {pull.body}")
    return issue_numbers

def recognize_hash_code(text):
    regex = r'\b[0-9a-fA-F]{32,}\b' # Matches any 32 or more hexadecimal characters
    match = re.search(regex, text)
    if match:
        return match.group(0)
    else:
        return None

# Recognize the web address in the text
def recognize_web_address(text):
    regex = r'\b(https?://|www\.)\S+\b' # Matches URLs starting with http://, https:// or www.
    match = re.search(regex, text)
    if match:
        return match.group(0)
    else:
        return None

# Recognize the web addresses in the text
def recognize_web_addresses(text):
    url_pattern = re.compile(r'http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+#]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+')
    # text = "Check out my website at https://www.example.com and my blog at http://blog.example.com!"
    urls = re.findall(url_pattern, text)
    return urls